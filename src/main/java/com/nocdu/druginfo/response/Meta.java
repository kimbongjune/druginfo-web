package com.nocdu.druginfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 검색 API 응답 객체
 * 메타 정보를 갖고있다.
 */
@Getter
@Setter
@Data
public class Meta {
    /** 의약품 검색 총 건수 **/
    private int total_count;

    /** 의약품 검색 다음페이지 존재 여부 **/
    @JsonProperty(namespace = "is_end")
    private boolean is_end;
}