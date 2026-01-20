package com.nocdu.druginfo.batch.processor;

import com.nocdu.druginfo.domain.drug.DrugDurWarning;
import com.nocdu.druginfo.infrastructure.api.dto.DurElderlyApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * DUR 노인주의 API 응답 -> DrugDurWarning 엔티티 변환 Processor
 */
@Slf4j
@Component
public class DurElderlyProcessor 
        implements ItemProcessor<DurElderlyApiResponse.Item, DrugDurWarning> {

    @Override
    public DrugDurWarning process(DurElderlyApiResponse.Item item) throws Exception {
        if (!StringUtils.hasText(item.getItemSeq())) {
            log.warn("Skipping DUR elderly item without itemSeq");
            return null;
        }

        return DrugDurWarning.builder()
                .itemSeq(item.getItemSeq())
                .itemName(item.getItemName())
                .entpName(item.getEntpName())
                .mainIngrCode(item.getMainIngrCode())
                .mainIngrKor(item.getMainIngrKor())
                .warningType(DrugDurWarning.WarningType.ELDERLY_CAUTION)
                .prohbtContent(item.getProhbtContent())
                .remark(item.getRemark())
                .typeCode(item.getTypeCode())
                .typeName(item.getTypeName())
                .build();
    }
}
