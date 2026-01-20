package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.domain.drug.DrugInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugInteractionRepository extends JpaRepository<DrugInteraction, Long> {
    
    List<DrugInteraction> findByItemSeq(String itemSeq);
    
    Optional<DrugInteraction> findByItemSeqAndMixtureItemSeq(String itemSeq, String mixtureItemSeq);
    
    void deleteByItemSeq(String itemSeq);
}
