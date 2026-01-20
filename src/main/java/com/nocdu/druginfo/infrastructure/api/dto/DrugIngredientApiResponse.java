package com.nocdu.druginfo.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 의약품 성분약효정보 API 응답 DTO
 * API: 건강보험심사평가원 의약품성분약효정보조회서비스
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrugIngredientApiResponse {

    private Header header;
    private Body body;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        private int pageNo;
        private int numOfRows;
        private int totalCount;
        private List<Item> items;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        /** 주성분코드 */
        @JsonProperty("CPNT_CD")
        private String componentCode;

        /** 주성분명 (한글) */
        @JsonProperty("CPNT_KOR_NM")
        private String componentKorName;

        /** 주성분명 (영문) */
        @JsonProperty("CPNT_ENG_NM")
        private String componentEngName;

        /** 약효분류코드 */
        @JsonProperty("FOML_CD")
        private String formulaCode;

        /** 약효분류명 */
        @JsonProperty("FOML_NM")
        private String formulaName;

        /** 투여경로 */
        @JsonProperty("ADM_ROUTE")
        private String admRoute;

        /** ATC코드 */
        @JsonProperty("ATC_CODE")
        private String atcCode;

        /** ATC명칭 */
        @JsonProperty("ATC_NAME")
        private String atcName;
    }
}
