package com.nocdu.druginfo.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DUR 병용금기 정보 API 응답 DTO
 * API: 의약품 안전사용서비스(DUR) 품목정보 - 병용금기
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DurCombinedTabooApiResponse {

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

        /** 품목명 */
        @JsonProperty("ITEM_NAME")
        private String itemName;

        /** 업체명 */
        @JsonProperty("ENTP_NAME")
        private String entpName;

        /** 주성분코드 */
        @JsonProperty("MAIN_INGR_CODE")
        private String mainIngrCode;

        /** 주성분명 (한글) */
        @JsonProperty("MAIN_INGR_KOR")
        private String mainIngrKor;

        /** 주성분명 (영문) */
        @JsonProperty("MAIN_INGR_ENG")
        private String mainIngrEng;

        /** 병용금기 품목기준코드 */
        @JsonProperty("MIXTURE_ITEM_SEQ")
        private String mixtureItemSeq;

        /** 병용금기 품목명 */
        @JsonProperty("MIXTURE_ITEM_NAME")
        private String mixtureItemName;

        /** 병용금기 업체명 */
        @JsonProperty("MIXTURE_ENTP_NAME")
        private String mixtureEntpName;

        /** 병용금기 주성분코드 */
        @JsonProperty("MIXTURE_MAIN_INGR_CODE")
        private String mixtureMainIngrCode;

        /** 병용금기 주성분명 */
        @JsonProperty("MIXTURE_MAIN_INGR")
        private String mixtureMainIngr;

        /** 금기등급 */
        @JsonProperty("PROHBT_CONTENT")
        private String prohbtContent;

        /** 금기사유 */
        @JsonProperty("REMARK")
        private String remark;

        /** 상세정보 */
        @JsonProperty("INGR_CODE")
        private String ingrCode;

        /** DUR유형코드 */
        @JsonProperty("TYPE_CODE")
        private String typeCode;

        /** DUR유형명 */
        @JsonProperty("TYPE_NAME")
        private String typeName;
    }
}
