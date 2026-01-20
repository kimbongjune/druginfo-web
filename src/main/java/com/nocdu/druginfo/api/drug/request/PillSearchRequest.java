package com.nocdu.druginfo.api.drug.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 낱알 식별 검색 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PillSearchRequest {

    /** 모양 (원형, 타원형, 장방형 등) */
    private String shape;

    /** 색상 (앞/뒤 통합 검색) */
    private String color;

    /** 앞면 색상 */
    private String colorFront;

    /** 뒷면 색상 */
    private String colorBack;

    /** 앞면 식별문구 */
    private String printFront;

    /** 뒷면 식별문구 */
    private String printBack;

    /** 제형 (정제, 캡슐 등) */
    private String formCodeName;

    /** 분할선 */
    private String line;

    /** 분류명 */
    private String className;
}
