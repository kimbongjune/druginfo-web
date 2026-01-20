package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.domain.drug.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 의약품 기본 JPA Repository
 */
@Repository
public interface DrugRepository extends JpaRepository<Drug, Long>, DrugRepositoryCustom {

    /**
     * 품목기준코드로 의약품 조회
     */
    Optional<Drug> findByItemSeq(String itemSeq);

    /**
     * 품목기준코드 존재 여부 확인
     */
    boolean existsByItemSeq(String itemSeq);

    /**
     * 품목기준코드로 삭제
     */
    void deleteByItemSeq(String itemSeq);
}
