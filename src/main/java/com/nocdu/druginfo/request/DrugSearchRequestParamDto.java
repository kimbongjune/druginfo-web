package com.nocdu.druginfo.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 검색 API 요청 객체
 */
@Getter
@Setter
@Data
@ToString
public class DrugSearchRequestParamDto {
    /** 의약품 검색 문자 **/
    private String query;
    /** 의약품 검색 페이지 **/
    private int page;
    /** 의약품 한 페이지당 반환 건수 **/
    private int limitPageSize = 15;
    /** 의약품 모양 **/
    private String shape;
    /** 제형코드이름 **/
    private String dosageForm;
    /** 의약품 식별 표시(앞) **/
    private String printFront;
    /** 의약품 식별 표시(뒤) **/
    private String printBack;
    /** 의약품 색생 **/
    private String colorClass;
    /** 의약품 분할선 **/
    private String line;
}
