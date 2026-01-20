package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.domain.drug.DrugSafetyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugSafetyInfoRepository extends JpaRepository<DrugSafetyInfo, Long> {
    Optional<DrugSafetyInfo> findByDrugId(Long drugId);
}
