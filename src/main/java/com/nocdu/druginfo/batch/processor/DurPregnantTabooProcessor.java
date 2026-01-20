package com.nocdu.druginfo.batch.processor;

import com.nocdu.druginfo.domain.drug.DrugDurWarning;
import com.nocdu.druginfo.infrastructure.api.dto.DurPregnantTabooApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * DUR 임부금기 API 응답 -> DrugDurWarning 엔티티 변환 Processor
 */
@Slf4j
@Component
public class DurPregnantTabooProcessor 
        implements ItemProcessor<DurPregnantTabooApiResponse.Item, DrugDurWarning> {

    @Override
    public DrugDurWarning process(DurPregnantTabooApiResponse.Item item) throws Exception {
        if (!StringUtils.hasText(item.getItemSeq())) {
            log.warn("Skipping DUR pregnant taboo item without itemSeq");
            return null;
        }

        return DrugDurWarning.builder()
                .itemSeq(item.getItemSeq())
                .itemName(item.getItemName())
                .entpName(item.getEntpName())
                .mainIngrCode(item.getMainIngrCode())
                .mainIngrKor(item.getMainIngrKor())
                .warningType(DrugDurWarning.WarningType.PREGNANT_TABOO)
                .prohbtContent(item.getProhbtContent())
                .remark(item.getRemark())
                .typeCode(item.getTypeCode())
                .typeName(item.getTypeName())
                .build();
    }
}
