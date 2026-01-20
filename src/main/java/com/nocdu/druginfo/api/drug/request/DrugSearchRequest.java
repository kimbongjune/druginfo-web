package com.nocdu.druginfo.api.drug.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 의약품 통합 검색 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugSearchRequest {

    /** 검색어 (의약품명, 제조사, 효능 통합 검색) */
    private String query;

    /** 의약품명 (정확한 검색) */
    private String itemName;

    /** 제조사명 */
    private String entpName;

    /** 효능 */
    private String efficacy;

    /** 분류명 */
    private String className;

    /** 전문/일반 구분 */
    private String etcOtcName;
}
