package com.nocdu.druginfo.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 검색 API 응답 객체
 * 의약품 정보 리스트와 메타 정보를 담고있다.
 */
@Getter
@Setter
@Data
public class DrugSearchResultDto {
    /** API 메타 정보 **/
    private Meta meta;
    /** API 본문 **/
    private List<Documents> documents;

}