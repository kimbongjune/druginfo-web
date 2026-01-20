package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.domain.drug.DrugIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 낱알식별정보 Repository
 */
@Repository
public interface DrugIdentificationRepository extends JpaRepository<DrugIdentification, Long> {
    
    Optional<DrugIdentification> findByItemSeq(String itemSeq);
    
    /**
     * 중복 제거된 분류번호(classNo) 목록 조회
     * 성분정보 API 조회 시 meftDivNo 파라미터로 사용
     */
    @Query("SELECT DISTINCT d.classNo FROM DrugIdentification d WHERE d.classNo IS NOT NULL")
    List<String> findDistinctClassNo();
}
