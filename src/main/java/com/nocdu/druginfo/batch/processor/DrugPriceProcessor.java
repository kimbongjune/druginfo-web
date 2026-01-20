package com.nocdu.druginfo.batch.processor;

import com.nocdu.druginfo.domain.drug.DrugPrice;
import com.nocdu.druginfo.infrastructure.api.dto.DrugPriceApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * 약가기준정보 API 응답 -> DrugPrice 엔티티 변환 Processor
 */
@Slf4j
@Component
public class DrugPriceProcessor 
        implements ItemProcessor<DrugPriceApiResponse.Item, DrugPrice> {

    @Override
    public DrugPrice process(DrugPriceApiResponse.Item item) throws Exception {
        if (!StringUtils.hasText(item.getEdiCode())) {
            log.warn("Skipping drug price item without ediCode");
            return null;
        }

        return DrugPrice.builder()
                .itemSeq(item.getItemSeq())
                .itemName(item.getItemName())
                .entpName(item.getEntpName())
                .ediCode(item.getEdiCode())
                .drugAmount(parseBigDecimal(item.getDrugAmount()))
                .unit(item.getUnit())
                .spec(item.getSpec())
                .applyStartDate(item.getApplyStartDate())
                .applyEndDate(item.getApplyEndDate())
                .payType(item.getPayType())
                .maxPrice(parseBigDecimal(item.getMaxPrice()))
                .gnlNmCd(item.getGnlNmCd())
                .build();
    }

    private BigDecimal parseBigDecimal(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return new BigDecimal(value.trim().replaceAll(",", ""));
        } catch (NumberFormatException e) {
            log.debug("Could not parse BigDecimal: {}", value);
            return null;
        }
    }
}
