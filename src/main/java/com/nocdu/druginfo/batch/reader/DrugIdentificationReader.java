package com.nocdu.druginfo.batch.reader;

import com.nocdu.druginfo.infrastructure.api.OpenApiClient;
import com.nocdu.druginfo.infrastructure.api.dto.DrugIdentificationApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * 의약품 낱알식별정보 API ItemReader
 * - 공공데이터 API에서 페이징 방식으로 데이터 조회
 * - Stateful Reader: 현재 페이지 상태 유지
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugIdentificationReader implements ItemReader<DrugIdentificationApiResponse.Item> {

    private final OpenApiClient openApiClient;

    @Value("${openapi.mfds.endpoints.drug-identification}")
    private String drugIdentificationEndpoint;

    private static final int PAGE_SIZE = 100;
    private int currentPage = 1;
    private Iterator<DrugIdentificationApiResponse.Item> currentItems;
    private boolean hasMoreData = true;

    @Override
    public DrugIdentificationApiResponse.Item read() {
        // 현재 페이지의 데이터를 모두 사용했거나 첫 호출인 경우 다음 페이지 조회
        if (currentItems == null || !currentItems.hasNext()) {
            if (!hasMoreData) {
                reset(); // 다음 Job 실행을 위해 상태 초기화
                return null;
            }
            fetchNextPage();
        }

        // 더 이상 데이터가 없으면 null 반환 (배치 종료)
        if (currentItems == null || !currentItems.hasNext()) {
            reset();
            return null;
        }

        return currentItems.next();
    }

    private void fetchNextPage() {
        try {
            log.info("Fetching drug identification page {} (size: {})", currentPage, PAGE_SIZE);

            DrugIdentificationApiResponse response = openApiClient.fetchDrugIdentification(
                    drugIdentificationEndpoint, currentPage, PAGE_SIZE);

            if (response != null && response.getBody() != null) {
                List<DrugIdentificationApiResponse.Item> items = response.getBody().getItems();

                if (items == null || items.isEmpty()) {
                    hasMoreData = false;
                    currentItems = null;
                    log.info("No more drug identification data available. Total pages fetched: {}", currentPage - 1);
                } else {
                    currentItems = items.iterator();
                    log.info("Fetched {} drug identification items from page {}", items.size(), currentPage);

                    // 전체 건수 대비 현재 페이지 확인
                    int totalCount = response.getBody().getTotalCount();
                    int fetchedCount = currentPage * PAGE_SIZE;
                    if (fetchedCount >= totalCount) {
                        hasMoreData = false;
                    }
                    currentPage++;
                }
            } else {
                hasMoreData = false;
                currentItems = null;
                log.warn("Empty or null response from drug identification API");
            }
        } catch (Exception e) {
            log.error("Error fetching drug identification page {}: {}", currentPage, e.getMessage());
            hasMoreData = false;
            currentItems = null;
        }
    }

    /**
     * 배치 재실행을 위한 상태 초기화
     */
    public void reset() {
        currentPage = 1;
        currentItems = null;
        hasMoreData = true;
    }
}
