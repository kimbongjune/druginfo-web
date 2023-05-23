package com.nocdu.druginfo.druginfo.service;

import com.nocdu.druginfo.drugdetailinfo.entity.DrugDetailInfoEntity;
import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.druginfo.repository.DrugInfoRepository;
import com.nocdu.druginfo.pillinfo.entity.PillInfoEntity;
import com.nocdu.druginfo.request.DrugSearchRequestParamDto;
import com.nocdu.druginfo.response.Documents;
import com.nocdu.druginfo.restcontroller.DrugInfoRestController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 정보 API 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class DrugInfoService {

    private final EntityManager entityManager;

    private final DrugInfoRepository drugInfoRepository;

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(DrugInfoService.class);

    /**
     * 아이디를 이용해 의약품 기본정보를 단건 조회한다.
     * @param id Long
     * @return Optional<DrugInfoEntity>
     */
    public Optional<DrugInfoEntity> getOneDrugInfo(Long id) {
        return drugInfoRepository.findById(id);
    }

    /**
     * 의약품 기본정보 목록 특정 객체를 조회한다.
     * @param dto DrugSearchParamVO
     * @param pageable Pageable
     * @return Page<Documents>
     */
    public Page<Documents> selectDrugInfoSearchList(DrugSearchRequestParamDto dto, Pageable pageable){
        return drugInfoRepository.selectDrugInfoSearchList(dto, pageable);
    }
}
