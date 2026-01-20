package com.nocdu.druginfo.infrastructure.api;

import com.nocdu.druginfo.infrastructure.api.dto.DrugIdentificationApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 공공데이터 API 공통 클라이언트
 * 식품의약품안전처(MFDS) 및 건강보험심사평가원(HIRA) API 지원
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpenApiClient {

    private final WebClient webClient;

    @Value("${openapi.service-key}")
    private String serviceKey;

    @Value("${openapi.mfds.base-url}")
    private String mfdsBaseUrl;

    @Value("${openapi.hira.base-url}")
    private String hiraBaseUrl;

    /**
     * 식품의약품안전처(MFDS) API 페이지별 호출
     */
    public <T> T fetchMfdsPage(String endpoint, int pageNo, int numOfRows,
                                Map<String, String> additionalParams, Class<T> responseType) {
        return fetchPage(mfdsBaseUrl, endpoint, pageNo, numOfRows, additionalParams, responseType);
    }

    /**
     * 건강보험심사평가원(HIRA) API 페이지별 호출
     */
    public <T> T fetchHiraPage(String endpoint, int pageNo, int numOfRows,
                                Map<String, String> additionalParams, Class<T> responseType) {
        return fetchPage(hiraBaseUrl, endpoint, pageNo, numOfRows, additionalParams, responseType);
    }

    /**
     * 공통 페이지별 API 호출 (JSON 응답용 - MFDS API)
     */
    private <T> T fetchPage(String baseUrl, String endpoint, int pageNo, int numOfRows,
                            Map<String, String> additionalParams, Class<T> responseType) {
        try {
            // 서비스키를 URL 인코딩
            String encodedServiceKey = java.net.URLEncoder.encode(serviceKey, java.nio.charset.StandardCharsets.UTF_8);
            
            String urlString = baseUrl + endpoint 
                    + "?serviceKey=" + encodedServiceKey
                    + "&type=json"
                    + "&pageNo=" + pageNo
                    + "&numOfRows=" + numOfRows;

            if (additionalParams != null) {
                for (Map.Entry<String, String> entry : additionalParams.entrySet()) {
                    if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                        urlString += "&" + entry.getKey() + "=" 
                                + java.net.URLEncoder.encode(entry.getValue(), java.nio.charset.StandardCharsets.UTF_8);
                    }
                }
            }

            java.net.URI uri = java.net.URI.create(urlString);
            log.info("Fetching page {} with {} rows from {}", pageNo, numOfRows, 
                    uri.toString().replaceAll("serviceKey=[^&]+", "serviceKey=***"));

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();

        } catch (Exception e) {
            log.error("API call failed for endpoint: {}", endpoint, e);
            throw new RuntimeException("Failed to fetch data from API: " + endpoint, e);
        }
    }

    /**
     * MFDS API 단일 호출
     */
    public <T> T fetchMfds(String endpoint, Map<String, String> params, Class<T> responseType) {
        return fetch(mfdsBaseUrl, endpoint, params, responseType);
    }

    /**
     * HIRA API 단일 호출
     */
    public <T> T fetchHira(String endpoint, Map<String, String> params, Class<T> responseType) {
        return fetch(hiraBaseUrl, endpoint, params, responseType);
    }

    /**
     * 공통 API 호출
     */
    private <T> T fetch(String baseUrl, String endpoint, Map<String, String> params, Class<T> responseType) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(baseUrl + endpoint)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("type", "json");

            if (params != null) {
                params.forEach((key, value) -> {
                    if (value != null && !value.isEmpty()) {
                        builder.queryParam(key, value);
                    }
                });
            }

            URI uri = builder.build(true).toUri();
            log.debug("Calling API: {}", uri.toString().replaceAll("serviceKey=[^&]+", "serviceKey=***"));

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();

        } catch (Exception e) {
            log.error("API call failed for endpoint: {}", endpoint, e);
            throw new RuntimeException("Failed to fetch data from API: " + endpoint, e);
        }
    }

    /**
     * 의약품 낱알식별정보 API 호출
     */
    public DrugIdentificationApiResponse fetchDrugIdentification(String endpoint, int pageNo, int numOfRows) {
        return fetchMfdsPage(endpoint, pageNo, numOfRows, new HashMap<>(), DrugIdentificationApiResponse.class);
    }
}
