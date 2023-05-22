package com.nocdu.druginfo.pillinfo.service;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.druginfo.repository.DrugInfoRepository;
import com.nocdu.druginfo.pillinfo.entity.PillInfoEntity;
import com.nocdu.druginfo.pillinfo.repository.PillInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 정보 API 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class PillInfoService {

    private final PillInfoRepository pillInfoRepository;

    public Optional<PillInfoEntity> getOneDrugPillInfo(Long id) {
        return pillInfoRepository.findById(id);
    }

}
