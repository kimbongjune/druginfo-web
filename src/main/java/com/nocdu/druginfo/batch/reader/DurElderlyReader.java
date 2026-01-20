package com.nocdu.druginfo.batch.reader;

import com.nocdu.druginfo.infrastructure.api.OpenApiClient;
import com.nocdu.druginfo.infrastructure.api.dto.DurElderlyApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * DUR 노인주의 정보 API ItemReader
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DurElderlyReader implements ItemReader<DurElderlyApiResponse.Item> {

    private final OpenApiClient openApiClient;

    @Value("${openapi.mfds.endpoints.dur-elderly-caution}")
    private String durElderlyCautionEndpoint;

    private static final int PAGE_SIZE = 100;
    private int currentPage = 1;
    private Iterator<DurElderlyApiResponse.Item> currentItems;
    private boolean hasMoreData = true;

    @Override
    public DurElderlyApiResponse.Item read() {
        if (currentItems == null || !currentItems.hasNext()) {
            if (!hasMoreData) {
                reset();
                return null;
            }
            fetchNextPage();
        }

        if (currentItems == null || !currentItems.hasNext()) {
            reset();
            return null;
        }

        return currentItems.next();
    }

    private void fetchNextPage() {
        try {
            log.info("Fetching DUR elderly caution page {} (size: {})", currentPage, PAGE_SIZE);

            DurElderlyApiResponse response = openApiClient.fetchMfdsPage(
                    durElderlyCautionEndpoint, currentPage, PAGE_SIZE, 
                    new HashMap<>(), DurElderlyApiResponse.class);

            if (response != null && response.getBody() != null) {
                List<DurElderlyApiResponse.Item> items = response.getBody().getItems();

                if (items == null || items.isEmpty()) {
                    hasMoreData = false;
                    currentItems = null;
                    log.info("DUR elderly caution data fetch complete. Total pages: {}", currentPage - 1);
                } else {
                    currentItems = items.iterator();
                    log.info("Fetched {} DUR elderly caution items from page {}", items.size(), currentPage);

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
            }
        } catch (Exception e) {
            log.error("Error fetching DUR elderly caution page {}: {}", currentPage, e.getMessage());
            hasMoreData = false;
            currentItems = null;
        }
    }

    public void reset() {
        currentPage = 1;
        currentItems = null;
        hasMoreData = true;
    }
}
