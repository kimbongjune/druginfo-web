package com.nocdu.druginfo.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 의약품 낱알식별정보 API 응답 DTO
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrugIdentificationApiResponse {

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

        /** 업체일련번호 */
        @JsonProperty("ENTP_SEQ")
        private String entpSeq;

        /** 업체명 */
        @JsonProperty("ENTP_NAME")
        private String entpName;

        /** 성상 */
        @JsonProperty("CHART")
        private String chart;

        /** 의약품 이미지 */
        @JsonProperty("ITEM_IMAGE")
        private String itemImage;

        /** 표시(앞) */
        @JsonProperty("PRINT_FRONT")
        private String printFront;

        /** 표시(뒷) */
        @JsonProperty("PRINT_BACK")
        private String printBack;

        /** 의약품제형 */
        @JsonProperty("DRUG_SHAPE")
        private String drugShape;

        /** 색상(앞) */
        @JsonProperty("COLOR_CLASS1")
        private String colorClass1;

        /** 색상(뒤) */
        @JsonProperty("COLOR_CLASS2")
        private String colorClass2;

        /** 분할선(앞) */
        @JsonProperty("LINE_FRONT")
        private String lineFront;

        /** 분할선(뒤) */
        @JsonProperty("LINE_BACK")
        private String lineBack;

        /** 크기(장축) */
        @JsonProperty("LENG_LONG")
        private String lengLong;

        /** 크기(단축) */
        @JsonProperty("LENG_SHORT")
        private String lengShort;

        /** 크기(두께) */
        @JsonProperty("THICK")
        private String thick;

        /** 이미지등록일 */
        @JsonProperty("IMG_REGIST_TS")
        private String imgRegistTs;

        /** 분류번호 */
        @JsonProperty("CLASS_NO")
        private String classNo;

        /** 분류명 */
        @JsonProperty("CLASS_NAME")
        private String className;

        /** 전문/일반 */
        @JsonProperty("ETC_OTC_NAME")
        private String etcOtcName;

        /** 품목허가일자 */
        @JsonProperty("ITEM_PERMIT_DATE")
        private String itemPermitDate;

        /** 제형코드이름 */
        @JsonProperty("FORM_CODE_NAME")
        private String formCodeName;

        /** 마크내용(앞) */
        @JsonProperty("MARK_CODE_FRONT_ANAL")
        private String markCodeFrontAnal;

        /** 마크내용(뒤) */
        @JsonProperty("MARK_CODE_BACK_ANAL")
        private String markCodeBackAnal;

        /** 마크이미지(앞) */
        @JsonProperty("MARK_CODE_FRONT_IMG")
        private String markCodeFrontImg;

        /** 마크이미지(뒤) */
        @JsonProperty("MARK_CODE_BACK_IMG")
        private String markCodeBackImg;

        /** 제품영문명 */
        @JsonProperty("ITEM_ENG_NAME")
        private String itemEngName;

        /** 변경일자 */
        @JsonProperty("CHANGE_DATE")
        private String changeDate;

        /** 마크코드(앞) */
        @JsonProperty("MARK_CODE_FRONT")
        private String markCodeFront;

        /** 마크코드(뒤) */
        @JsonProperty("MARK_CODE_BACK")
        private String markCodeBack;

        /** EDI코드 */
        @JsonProperty("EDI_CODE")
        private String ediCode;
    }
}
