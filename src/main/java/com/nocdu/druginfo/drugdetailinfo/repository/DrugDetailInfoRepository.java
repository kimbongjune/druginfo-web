package com.nocdu.druginfo.drugdetailinfo.repository;

import com.nocdu.druginfo.drugdetailinfo.entity.DrugDetailInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 상세정보 데이터베이스 접근 JPA 인터페이스 클래스
 */
public interface DrugDetailInfoRepository extends JpaRepository<DrugDetailInfoEntity, String> {

    @Override
    Optional<DrugDetailInfoEntity> findById(String s);
}
