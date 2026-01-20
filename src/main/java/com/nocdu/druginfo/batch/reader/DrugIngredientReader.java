package com.nocdu.druginfo.batch.reader;

import com.nocdu.druginfo.domain.drug.repository.DrugPriceRepository;
import com.nocdu.druginfo.infrastructure.api.dto.DrugIngredientApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 의약품 성분약효정보 API ItemReader
 * 건강보험심사평가원 API
 * - DrugPrice에서 조회한 일반명코드(gnlNmCd)를 사용하여 조회
 * - XML 응답만 지원하므로 XML 파싱 사용
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugIngredientReader implements ItemReader<DrugIngredientApiResponse.Item> {

    private final DrugPriceRepository drugPriceRepository;
    private final WebClient webClient;

    @Value("${openapi.service-key}")
    private String serviceKey;

    @Value("${openapi.hira.base-url}")
    private String hiraBaseUrl;

    @Value("${openapi.hira.endpoints.ingredient-effect-info}")
    private String ingredientEffectEndpoint;

    private static final int PAGE_SIZE = 100;
    
    // DB에서 조회한 일반명코드 목록
    private List<String> gnlNmCdList;
    private int currentGnlNmCdIndex = 0;
    
    // 현재 일반명코드에 대한 API 응답 아이템들
    private Iterator<DrugIngredientApiResponse.Item> currentItems;
    private int currentPage = 1;
    private boolean hasMorePages = true;
    private boolean initialized = false;

    @Override
    public DrugIngredientApiResponse.Item read() {
        // 초기화
        if (!initialized) {
            initialize();
        }
        
        while (currentGnlNmCdIndex < gnlNmCdList.size()) {
            // 현재 일반명코드의 현재 페이지에서 데이터가 있으면 반환
            if (currentItems != null && currentItems.hasNext()) {
                return currentItems.next();
            }
            
            // 현재 일반명코드의 다음 페이지 fetch
            if (hasMorePages) {
                fetchNextPage();
                if (currentItems != null && currentItems.hasNext()) {
                    return currentItems.next();
                }
            }
            
            // 다음 일반명코드로 이동
            moveToNextGnlNmCd();
        }
        
        log.info("Drug ingredient data fetch complete. Total gnlNmCd processed: {}", gnlNmCdList.size());
        reset();
        return null;
    }

    private void initialize() {
        gnlNmCdList = drugPriceRepository.findDistinctGnlNmCd();
        log.info("Retrieved {} distinct gnlNmCd from DrugPrice for ingredient lookup", gnlNmCdList.size());
        initialized = true;
        
        if (gnlNmCdList.isEmpty()) {
            log.warn("No gnlNmCd found in DrugPrice. Run DrugPrice ETL first.");
        }
    }

    private boolean debugLogged = false;

    private void fetchNextPage() {
        if (currentGnlNmCdIndex >= gnlNmCdList.size()) {
            hasMorePages = false;
            return;
        }
        
        String gnlNmCd = gnlNmCdList.get(currentGnlNmCdIndex);
        
        try {
            // URL 구성
            String encodedServiceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
            String encodedGnlNmCd = URLEncoder.encode(gnlNmCd, StandardCharsets.UTF_8);
            
            String urlString = hiraBaseUrl + ingredientEffectEndpoint
                    + "?serviceKey=" + encodedServiceKey
                    + "&numOfRows=" + PAGE_SIZE
                    + "&pageNo=" + currentPage
                    + "&gnlNmCd=" + encodedGnlNmCd;
            
            // URI 객체로 변환하여 이중 인코딩 방지
            java.net.URI uri = java.net.URI.create(urlString);
            
            log.info("Fetching drug ingredient gnlNmCd={} page {}", gnlNmCd, currentPage);
            
            // API 호출 및 XML 응답 받기
            String xmlResponse = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (xmlResponse == null || xmlResponse.trim().isEmpty()) {
                hasMorePages = false;
                currentItems = null;
                return;
            }

            // XML 파싱
            List<DrugIngredientApiResponse.Item> items = parseXmlResponse(xmlResponse, gnlNmCd);
            
            if (items == null || items.isEmpty()) {
                hasMorePages = false;
                currentItems = null;
            } else {
                currentItems = items.iterator();
                
                // 다음 페이지 여부 확인
                int totalCount = parseTotalCount(xmlResponse);
                int fetchedCount = currentPage * PAGE_SIZE;
                hasMorePages = fetchedCount < totalCount;
                currentPage++;
                
                log.debug("Fetched {} ingredient items for gnlNmCd={} (total: {})", items.size(), gnlNmCd, totalCount);
            }
            
        } catch (Exception e) {
            log.warn("Error fetching drug ingredient for gnlNmCd={}: {}", gnlNmCd, e.getMessage());
            hasMorePages = false;
            currentItems = null;
        }
    }
    
    private void moveToNextGnlNmCd() {
        currentGnlNmCdIndex++;
        currentPage = 1;
        hasMorePages = true;
        currentItems = null;
    }

    private List<DrugIngredientApiResponse.Item> parseXmlResponse(String xmlResponse, String currentGnlNmCd) {
        List<DrugIngredientApiResponse.Item> items = new ArrayList<>();
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));
            
            // 에러 체크
            NodeList resultCodeList = doc.getElementsByTagName("resultCode");
            if (resultCodeList.getLength() > 0) {
                String resultCode = resultCodeList.item(0).getTextContent();
                if (!"00".equals(resultCode)) {
                    NodeList resultMsgList = doc.getElementsByTagName("resultMsg");
                    String resultMsg = resultMsgList.getLength() > 0 ? resultMsgList.item(0).getTextContent() : "";
                    log.warn("API error for gnlNmCd={}: {} - {}", currentGnlNmCd, resultCode, resultMsg);
                    return items;
                }
            }
            
            NodeList itemList = doc.getElementsByTagName("item");
            
            for (int i = 0; i < itemList.getLength(); i++) {
                Element itemElement = (Element) itemList.item(i);
                DrugIngredientApiResponse.Item item = new DrugIngredientApiResponse.Item();
                
                // XML 태그 매핑 (실제 응답 로그 기반)
                // <gnlNmCd> -> ComponentCode
                item.setComponentCode(getElementText(itemElement, "gnlNmCd"));
                
                // <gnlNm> -> ComponentEngName (한글명은 응답에 없음)
                item.setComponentEngName(getElementText(itemElement, "gnlNm"));
                
                // <meftDivNo> -> FormulaCode (약효분류번호)
                item.setFormulaCode(getElementText(itemElement, "meftDivNo"));
                
                // <divNm> -> FormulaName (약효분류명)
                item.setFormulaName(getElementText(itemElement, "divNm"));
                
                // <injcPthCdNm> -> AdmRoute (투여경로)
                item.setAdmRoute(getElementText(itemElement, "injcPthCdNm"));
                
                // ATC 코드는 응답에 포함되지 않음 (필요 시 별도 매핑 테이블 사용)
                // item.setAtcCode(...);
                // item.setAtcName(...);

                items.add(item);
            }
            
        } catch (Exception e) {
            log.error("Error parsing XML response: {}", e.getMessage());
        }
        
        return items;
    }

    private int parseTotalCount(String xmlResponse) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));
            
            NodeList totalCountList = doc.getElementsByTagName("totalCount");
            if (totalCountList.getLength() > 0) {
                return Integer.parseInt(totalCountList.item(0).getTextContent());
            }
        } catch (Exception e) {
            // ignore
        }
        return 0;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null; // or empty string
    }

    public void reset() {
        gnlNmCdList = null;
        currentGnlNmCdIndex = 0;
        currentPage = 1;
        currentItems = null;
        hasMorePages = true;
        initialized = false;
    }
}
