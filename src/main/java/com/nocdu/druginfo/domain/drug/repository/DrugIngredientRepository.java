package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.domain.drug.DrugIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugIngredientRepository extends JpaRepository<DrugIngredient, Long> {
    
    List<DrugIngredient> findByComponentCode(String componentCode);
    
    Optional<DrugIngredient> findFirstByComponentCode(String componentCode);
}
