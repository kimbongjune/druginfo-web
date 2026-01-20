package com.nocdu.druginfo.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 의약품개요정보(e약은요) API 응답 DTO
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrugEasyInfoApiResponse {

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
        /** 업체명 */
        private String entpName;

        /** 제품명 */
        private String itemName;

        /** 품목기준코드 */
        private String itemSeq;

        /** 효능 */
        private String efcyQesitm;

        /** 사용법 */
        private String useMethodQesitm;

        /** 주의사항(경고) */
        private String atpnWarnQesitm;

        /** 주의사항 */
        private String atpnQesitm;

        /** 상호작용 */
        private String intrcQesitm;

        /** 부작용 */
        private String seQesitm;

        /** 보관법 */
        private String depositMethodQesitm;

        /** 공개일자 */
        private String openDe;

        /** 수정일자 */
        private String updateDe;

        /** 의약품 이미지 */
        private String itemImage;
    }
}
