package com.nocdu.druginfo.batch.processor;

import com.nocdu.druginfo.domain.drug.DrugDurWarning;
import com.nocdu.druginfo.infrastructure.api.dto.DurAgeTabooApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * DUR 특정연령대금기 API 응답 -> DrugDurWarning 엔티티 변환 Processor
 */
@Slf4j
@Component
public class DurAgeTabooProcessor 
        implements ItemProcessor<DurAgeTabooApiResponse.Item, DrugDurWarning> {

    @Override
    public DrugDurWarning process(DurAgeTabooApiResponse.Item item) throws Exception {
        if (!StringUtils.hasText(item.getItemSeq())) {
            log.warn("Skipping DUR age taboo item without itemSeq");
            return null;
        }

        return DrugDurWarning.builder()
                .itemSeq(item.getItemSeq())
                .itemName(item.getItemName())
                .entpName(item.getEntpName())
                .mainIngrCode(item.getMainIngrCode())
                .mainIngrKor(item.getMainIngrKor())
                .warningType(DrugDurWarning.WarningType.AGE_TABOO)
                .prohbtContent(item.getProhbtContent())
                .remark(item.getRemark())
                .typeCode(item.getTypeCode())
                .typeName(item.getTypeName())
                .build();
    }
}
