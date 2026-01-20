package com.nocdu.druginfo.batch.processor;

import com.nocdu.druginfo.domain.drug.DrugInteraction;
import com.nocdu.druginfo.infrastructure.api.dto.DurCombinedTabooApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * DUR 병용금기 API 응답 -> DrugInteraction 엔티티 변환 Processor
 */
@Slf4j
@Component
public class DurCombinedTabooProcessor 
        implements ItemProcessor<DurCombinedTabooApiResponse.Item, DrugInteraction> {

    @Override
    public DrugInteraction process(DurCombinedTabooApiResponse.Item item) throws Exception {
        if (!StringUtils.hasText(item.getItemSeq())) {
            log.warn("Skipping DUR combined taboo item without itemSeq");
            return null;
        }

        return DrugInteraction.builder()
                .itemSeq(item.getItemSeq())
                .itemName(item.getItemName())
                .entpName(item.getEntpName())
                .mainIngrCode(item.getMainIngrCode())
                .mainIngrKor(item.getMainIngrKor())
                .mixtureItemSeq(item.getMixtureItemSeq())
                .mixtureItemName(item.getMixtureItemName())
                .mixtureEntpName(item.getMixtureEntpName())
                .mixtureMainIngrCode(item.getMixtureMainIngrCode())
                .mixtureMainIngr(item.getMixtureMainIngr())
                .prohbtContent(item.getProhbtContent())
                .remark(item.getRemark())
                .typeCode(item.getTypeCode())
                .typeName(item.getTypeName())
                .build();
    }
}
