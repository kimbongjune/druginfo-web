package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.domain.drug.DrugDurWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugDurWarningRepository extends JpaRepository<DrugDurWarning, Long> {
    
    List<DrugDurWarning> findByItemSeq(String itemSeq);
    
    List<DrugDurWarning> findByItemSeqAndWarningType(String itemSeq, DrugDurWarning.WarningType warningType);
    
    Optional<DrugDurWarning> findByItemSeqAndWarningTypeAndTypeCode(
            String itemSeq, DrugDurWarning.WarningType warningType, String typeCode);
    
    void deleteByItemSeqAndWarningType(String itemSeq, DrugDurWarning.WarningType warningType);
}
