package com.nocdu.druginfo.batch.reader;

import com.nocdu.druginfo.infrastructure.api.OpenApiClient;
import com.nocdu.druginfo.infrastructure.api.dto.DrugEasyInfoApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * 의약품개요정보(e약은요) API ItemReader
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugEasyInfoReader implements ItemReader<DrugEasyInfoApiResponse.Item> {

    private final OpenApiClient openApiClient;

    @Value("${openapi.mfds.endpoints.drug-easy-info}")
    private String endpoint;

    private Iterator<DrugEasyInfoApiResponse.Item> currentItems;
    private int currentPage = 1;
    private int totalCount = -1;
    private int processedCount = 0;
    private static final int PAGE_SIZE = 100;

    @Override
    public DrugEasyInfoApiResponse.Item read() {
        // 현재 페이지의 아이템이 있으면 반환
        if (currentItems != null && currentItems.hasNext()) {
            processedCount++;
            return currentItems.next();
        }

        // 다음 페이지 로드
        if (totalCount == -1 || processedCount < totalCount) {
            fetchNextPage();
            if (currentItems != null && currentItems.hasNext()) {
                processedCount++;
                return currentItems.next();
            }
        }

        // 모든 데이터 처리 완료
        log.info("Completed reading all drug easy info. Total processed: {}", processedCount);
        reset();
        return null;
    }

    private void fetchNextPage() {
        try {
            log.info("Fetching page {} (processed: {}/{})", currentPage, processedCount, totalCount);

            DrugEasyInfoApiResponse response = openApiClient.fetchMfdsPage(
                    endpoint, currentPage, PAGE_SIZE, null, DrugEasyInfoApiResponse.class);

            if (response != null && response.getBody() != null) {
                DrugEasyInfoApiResponse.Body body = response.getBody();
                
                if (totalCount == -1) {
                    totalCount = body.getTotalCount();
                    log.info("Total items to fetch: {}", totalCount);
                }

                List<DrugEasyInfoApiResponse.Item> items = body.getItems();
                if (items != null && !items.isEmpty()) {
                    currentItems = items.iterator();
                    currentPage++;
                } else {
                    currentItems = null;
                }
            } else {
                log.warn("Empty response from API");
                currentItems = null;
            }
        } catch (Exception e) {
            log.error("Error fetching page {}: {}", currentPage, e.getMessage(), e);
            currentItems = null;
        }
    }

    /**
     * Reader 상태 초기화
     */
    public void reset() {
        currentItems = null;
        currentPage = 1;
        totalCount = -1;
        processedCount = 0;
    }
}

