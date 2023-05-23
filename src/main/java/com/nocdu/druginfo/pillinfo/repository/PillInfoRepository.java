package com.nocdu.druginfo.pillinfo.repository;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.pillinfo.entity.PillInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 식별정보 데이터베이스 접근 JPA 인터페이스 클래스
 * TODO 배치 프로그램을 이용해 인서트 로직을 작성할 예정이다.
 */
public interface PillInfoRepository extends JpaRepository<PillInfoEntity, Long> {

    @Override
    Optional<PillInfoEntity> findById(Long aLong);

}
