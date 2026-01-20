package com.nocdu.druginfo.batch.reader;

import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.domain.drug.repository.DrugRepository;
import com.nocdu.druginfo.infrastructure.api.dto.DrugPriceApiResponse;
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
 * 약가기준정보 API ItemReader
 * 건강보험심사평가원 API
 * - Drug 테이블에서 itemName을 가져와서 각각 API 호출
 * - XML 응답만 지원하므로 XML 파싱 사용
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugPriceReader implements ItemReader<DrugPriceApiResponse.Item> {

    private final DrugRepository drugRepository;
    private final WebClient webClient;

    @Value("${openapi.service-key}")
    private String serviceKey;

    @Value("${openapi.hira.base-url}")
    private String hiraBaseUrl;

    @Value("${openapi.hira.endpoints.drug-price-info}")
    private String drugPriceEndpoint;

    private static final int PAGE_SIZE = 100;
    
    // Drug 테이블에서 가져온 itemName 목록
    private Iterator<String> itemNameIterator;
    private String currentItemName;
    
    // 현재 itemName에 대한 API 응답 아이템들
    private Iterator<DrugPriceApiResponse.Item> currentItems;
    private int currentPage = 1;
    private boolean hasMorePages = false;
    
    private int processedCount = 0;
    private int totalItemNames = 0;
    private boolean initialized = false;

    @Override
    public DrugPriceApiResponse.Item read() {
        // 초기화
        if (!initialized) {
            initialize();
        }
        
        // 현재 아이템이 있으면 반환
        if (currentItems != null && currentItems.hasNext()) {
            return currentItems.next();
        }
        
        // 현재 itemName에 대해 더 많은 페이지가 있으면 가져오기
        if (hasMorePages) {
            fetchNextPage();
            if (currentItems != null && currentItems.hasNext()) {
                return currentItems.next();
            }
        }
        
        // 다음 itemName으로 이동
        while (itemNameIterator != null && itemNameIterator.hasNext()) {
            currentItemName = itemNameIterator.next();
            currentPage = 1;
            hasMorePages = true;
            processedCount++;
            
            if (processedCount % 100 == 0) {
                log.info("Drug price fetch progress: {}/{} item names processed", processedCount, totalItemNames);
            }
            
            fetchNextPage();
            
            if (currentItems != null && currentItems.hasNext()) {
                return currentItems.next();
            }
        }
        
        // 모든 itemName 처리 완료
        log.info("Drug price data fetch complete. Total item names processed: {}", processedCount);
        reset();
        return null;
    }

    private void initialize() {
        initialized = true;
        
        // Drug 테이블에서 모든 itemName 가져오기
        List<Drug> drugs = drugRepository.findAll();
        List<String> itemNames = new ArrayList<>();
        
        for (Drug drug : drugs) {
            if (drug.getItemName() != null && !drug.getItemName().trim().isEmpty()) {
                itemNames.add(drug.getItemName().trim());
            }
        }
        
        // 중복 제거
        Set<String> uniqueNames = new LinkedHashSet<>(itemNames);
        totalItemNames = uniqueNames.size();
        itemNameIterator = uniqueNames.iterator();
        
        log.info("Initialized DrugPriceReader with {} unique item names from Drug table", totalItemNames);
    }

    private void fetchNextPage() {
        if (currentItemName == null || currentItemName.isEmpty()) {
            hasMorePages = false;
            currentItems = null;
            return;
        }
        
        try {
            // URL 구성 (itmNm 파라미터 사용)
            // 서비스키는 이미 디코딩된 상태로 저장되어 있으므로 인코딩 필요
            String encodedServiceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
            String encodedItemName = URLEncoder.encode(currentItemName, StandardCharsets.UTF_8);
            
            String urlString = hiraBaseUrl + drugPriceEndpoint
                    + "?serviceKey=" + encodedServiceKey
                    + "&numOfRows=" + PAGE_SIZE
                    + "&pageNo=" + currentPage
                    + "&itmNm=" + encodedItemName;
            
            // URI 객체로 변환하여 이중 인코딩 방지
            java.net.URI uri = java.net.URI.create(urlString);
            
            log.debug("Fetching drug price for item: {} (page {})", currentItemName, currentPage);
            
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
            List<DrugPriceApiResponse.Item> items = parseXmlResponse(xmlResponse);
            
            if (items == null || items.isEmpty()) {
                hasMorePages = false;
                currentItems = null;
            } else {
                currentItems = items.iterator();
                
                // 다음 페이지 여부 확인 (totalCount 기반)
                int totalCount = parseTotalCount(xmlResponse);
                int fetchedCount = currentPage * PAGE_SIZE;
                hasMorePages = fetchedCount < totalCount;
                currentPage++;
                
                log.debug("Fetched {} price items for '{}' (total: {})", items.size(), currentItemName, totalCount);
            }
            
        } catch (Exception e) {
            log.warn("Error fetching drug price for '{}': {}", currentItemName, e.getMessage());
            hasMorePages = false;
            currentItems = null;
        }
    }

    /**
     * XML 응답을 파싱하여 Item 목록 반환
     */
    private List<DrugPriceApiResponse.Item> parseXmlResponse(String xmlResponse) {
        List<DrugPriceApiResponse.Item> items = new ArrayList<>();
        
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
                    String resultMsg = resultMsgList.getLength() > 0 ? resultMsgList.item(0).getTextContent() : "Unknown error";
                    log.warn("API error for '{}': {} - {}", currentItemName, resultCode, resultMsg);
                    return items;
                }
            }
            
            // item 요소들 파싱
            NodeList itemList = doc.getElementsByTagName("item");
            
            for (int i = 0; i < itemList.getLength(); i++) {
                Element itemElement = (Element) itemList.item(i);
                DrugPriceApiResponse.Item item = new DrugPriceApiResponse.Item();
                
                item.setItemSeq(getElementText(itemElement, "itemSeq"));
                item.setItemName(getElementText(itemElement, "itmNm"));
                item.setEntpName(getElementText(itemElement, "mnfEntpNm"));
                item.setEdiCode(getElementText(itemElement, "mdsCd"));
                item.setDrugAmount(getElementText(itemElement, "dgamt"));
                item.setUnit(getElementText(itemElement, "unt"));
                item.setSpec(getElementText(itemElement, "spc"));
                item.setApplyStartDate(getElementText(itemElement, "adtStaDd"));
                item.setApplyEndDate(getElementText(itemElement, "adtEndDd"));
                item.setPayType(getElementText(itemElement, "payTpNm"));
                item.setMaxPrice(getElementText(itemElement, "uprc"));
                item.setGnlNmCd(getElementText(itemElement, "gnlNmCd"));
                
                items.add(item);
            }
            
        } catch (Exception e) {
            log.error("Error parsing XML response: {}", e.getMessage());
        }
        
        return items;
    }

    /**
     * XML에서 totalCount 파싱
     */
    private int parseTotalCount(String xmlResponse) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));
            
            NodeList totalCountList = doc.getElementsByTagName("totalCount");
            if (totalCountList.getLength() > 0) {
                String totalCountStr = totalCountList.item(0).getTextContent();
                return Integer.parseInt(totalCountStr);
            }
        } catch (Exception e) {
            log.debug("Could not parse totalCount: {}", e.getMessage());
        }
        return 0;
    }

    /**
     * Element에서 특정 태그의 텍스트 가져오기
     */
    private String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    public void reset() {
        itemNameIterator = null;
        currentItemName = null;
        currentItems = null;
        currentPage = 1;
        hasMorePages = false;
        processedCount = 0;
        totalItemNames = 0;
        initialized = false;
    }
}
