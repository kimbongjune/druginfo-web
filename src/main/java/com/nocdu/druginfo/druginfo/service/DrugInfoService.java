package com.nocdu.druginfo.druginfo.service;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.druginfo.repository.DrugInfoRepository;
import com.nocdu.druginfo.requestparam.DrugSearchParamVO;
import com.nocdu.druginfo.response.Documents;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    private final DrugInfoRepository drugInfoRepository;

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
     * @param vo DrugSearchParamVO
     * @param pageable Pageable
     * @return Page<Documents>
     */
    public Page<Documents> selectDrugInfoSearchList(DrugSearchParamVO vo, Pageable pageable){
        return drugInfoRepository.selectDrugInfoSearchList(vo, pageable);
    }

}
