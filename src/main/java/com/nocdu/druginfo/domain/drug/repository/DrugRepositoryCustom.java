package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.api.drug.request.DrugSearchRequest;
import com.nocdu.druginfo.api.drug.request.PillSearchRequest;
import com.nocdu.druginfo.api.drug.response.DrugSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 의약품 QueryDSL Custom Repository 인터페이스
 */
public interface DrugRepositoryCustom {

    /**
     * 의약품 통합 검색 (이름, 제조사, 효능)
     */
    Page<DrugSearchResponse> searchDrugs(DrugSearchRequest request, Pageable pageable);

    /**
     * 낱알 식별 검색 (모양, 색상, 문구, 제형)
     */
    Page<DrugSearchResponse> searchByIdentification(PillSearchRequest request, Pageable pageable);
}
