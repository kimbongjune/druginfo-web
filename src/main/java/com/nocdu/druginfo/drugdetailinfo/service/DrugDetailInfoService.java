package com.nocdu.druginfo.drugdetailinfo.service;

import com.nocdu.druginfo.drugdetailinfo.entity.DrugDetailInfoEntity;
import com.nocdu.druginfo.drugdetailinfo.repository.DrugDetailInfoRepository;
import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.druginfo.repository.DrugInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 상세 정보 API 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class DrugDetailInfoService {

    private final DrugDetailInfoRepository drugDetailInfoRepository;

    public Optional<DrugDetailInfoEntity> getOneDrugDetailInfo(String id) {
        return drugDetailInfoRepository.findById(id);
    }

}
