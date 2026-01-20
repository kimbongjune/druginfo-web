package com.nocdu.druginfo.batch.processor;

import com.nocdu.druginfo.domain.drug.DrugIngredient;
import com.nocdu.druginfo.infrastructure.api.dto.DrugIngredientApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 의약품 성분약효정보 API 응답 -> DrugIngredient 엔티티 변환 Processor
 */
@Slf4j
@Component
public class DrugIngredientProcessor 
        implements ItemProcessor<DrugIngredientApiResponse.Item, DrugIngredient> {

    @Override
    public DrugIngredient process(DrugIngredientApiResponse.Item item) throws Exception {
        if (!StringUtils.hasText(item.getComponentCode())) {
            log.warn("Skipping drug ingredient item without componentCode");
            return null;
        }

        return DrugIngredient.builder()
                .componentCode(item.getComponentCode())
                .componentKorName(item.getComponentKorName())
                .componentEngName(item.getComponentEngName())
                .formulaCode(item.getFormulaCode())
                .formulaName(item.getFormulaName())
                .admRoute(item.getAdmRoute())
                .atcCode(item.getAtcCode())
                .atcName(item.getAtcName())
                .build();
    }
}
