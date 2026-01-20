package com.nocdu.druginfo.batch.writer;

import com.nocdu.druginfo.domain.drug.DrugIngredient;
import com.nocdu.druginfo.domain.drug.repository.DrugIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DrugIngredient 엔티티 DB 저장 Writer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugIngredientWriter implements ItemWriter<DrugIngredient> {

    private final DrugIngredientRepository drugIngredientRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends DrugIngredient> chunk) throws Exception {
        log.debug("Writing {} drug ingredient items", chunk.size());

        for (DrugIngredient ingredient : chunk) {
            try {
                // Upsert by componentCode
                Optional<DrugIngredient> existing = 
                        drugIngredientRepository.findFirstByComponentCode(ingredient.getComponentCode());

                if (existing.isPresent()) {
                    DrugIngredient e = existing.get();
                    e.setComponentKorName(ingredient.getComponentKorName());
                    e.setComponentEngName(ingredient.getComponentEngName());
                    e.setFormulaCode(ingredient.getFormulaCode());
                    e.setFormulaName(ingredient.getFormulaName());
                    e.setAdmRoute(ingredient.getAdmRoute());
                    e.setAtcCode(ingredient.getAtcCode());
                    e.setAtcName(ingredient.getAtcName());
                    drugIngredientRepository.save(e);
                } else {
                    drugIngredientRepository.save(ingredient);
                }
            } catch (Exception e) {
                log.error("Error writing drug ingredient {}: {}", ingredient.getComponentCode(), e.getMessage());
            }
        }
    }
}
