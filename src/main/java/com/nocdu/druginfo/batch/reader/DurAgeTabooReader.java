package com.nocdu.druginfo.batch.reader;

import com.nocdu.druginfo.infrastructure.api.OpenApiClient;
import com.nocdu.druginfo.infrastructure.api.dto.DurAgeTabooApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * DUR 특정연령대금기 정보 API ItemReader
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DurAgeTabooReader implements ItemReader<DurAgeTabooApiResponse.Item> {

    private final OpenApiClient openApiClient;

    @Value("${openapi.mfds.endpoints.dur-age-taboo}")
    private String durAgeTabooEndpoint;

    private static final int PAGE_SIZE = 100;
    private int currentPage = 1;
    private Iterator<DurAgeTabooApiResponse.Item> currentItems;
    private boolean hasMoreData = true;

    @Override
    public DurAgeTabooApiResponse.Item read() {
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
            log.info("Fetching DUR age taboo page {} (size: {})", currentPage, PAGE_SIZE);

            DurAgeTabooApiResponse response = openApiClient.fetchMfdsPage(
                    durAgeTabooEndpoint, currentPage, PAGE_SIZE, 
                    new HashMap<>(), DurAgeTabooApiResponse.class);

            if (response != null && response.getBody() != null) {
                List<DurAgeTabooApiResponse.Item> items = response.getBody().getItems();

                if (items == null || items.isEmpty()) {
                    hasMoreData = false;
                    currentItems = null;
                    log.info("DUR age taboo data fetch complete. Total pages: {}", currentPage - 1);
                } else {
                    currentItems = items.iterator();
                    log.info("Fetched {} DUR age taboo items from page {}", items.size(), currentPage);

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
            log.error("Error fetching DUR age taboo page {}: {}", currentPage, e.getMessage());
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
