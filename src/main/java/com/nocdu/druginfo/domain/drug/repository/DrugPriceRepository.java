package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.domain.drug.DrugPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugPriceRepository extends JpaRepository<DrugPrice, Long> {
    
    List<DrugPrice> findByItemSeq(String itemSeq);
    
    Optional<DrugPrice> findByEdiCode(String ediCode);
    
    Optional<DrugPrice> findByItemSeqAndEdiCode(String itemSeq, String ediCode);

    /**
     * 중복 제거된 일반명코드(gnlNmCd) 목록 조회
     * 성분정보 API 조회 시 gnlNmCd 파라미터로 사용
     */
    @Query("SELECT DISTINCT d.gnlNmCd FROM DrugPrice d WHERE d.gnlNmCd IS NOT NULL")
    List<String> findDistinctGnlNmCd();
}
