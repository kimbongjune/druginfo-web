package com.nocdu.druginfo.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 약가기준정보 API 응답 DTO
 * API: 건강보험심사평가원 약가기준정보조회서비스
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrugPriceApiResponse {

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
        /** 품목기준코드 */
        @JsonProperty("ITEM_SEQ")
        private String itemSeq;

        /** 제품명 */
        @JsonProperty("ITEM_NAME")
        private String itemName;

        /** 업체명 */
        @JsonProperty("ENTP_NAME")
        private String entpName;

        /** 보험코드 */
        @JsonProperty("EDI_CODE")
        private String ediCode;

        /** 약가 (원) */
        @JsonProperty("DGAMT")
        private String drugAmount;

        /** 단위 */
        @JsonProperty("UNIT")
        private String unit;

        /** 규격 */
        @JsonProperty("SPEC")
        private String spec;

        /** 적용시작일 */
        @JsonProperty("APPLY_START_DATE")
        private String applyStartDate;

        /** 적용종료일 */
        @JsonProperty("APPLY_END_DATE")
        private String applyEndDate;

        /** 급여구분 (급여/비급여) */
        @JsonProperty("PAY_TYPE")
        private String payType;

        /** 상한가 */
        @JsonProperty("MAX_PRICE")
        private String maxPrice;

        /** 일반명코드 (성분약효정보 조회 시 사용) */
        @JsonProperty("GNL_NM_CD")
        private String gnlNmCd;
    }
}
