package com.nocdu.druginfo.batch.processor;

import com.nocdu.druginfo.domain.drug.DrugIdentification;
import com.nocdu.druginfo.infrastructure.api.dto.DrugIdentificationApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 낱알식별정보 API 응답 -> DrugIdentification 엔티티 변환 Processor
 */
@Slf4j
@Component
public class DrugIdentificationProcessor 
        implements ItemProcessor<DrugIdentificationApiResponse.Item, DrugIdentification> {

    @Override
    public DrugIdentification process(DrugIdentificationApiResponse.Item item) throws Exception {
        // 품목일련번호 필수 검증
        if (!StringUtils.hasText(item.getItemSeq())) {
            log.warn("Skipping drug identification item without itemSeq");
            return null;
        }

        return DrugIdentification.builder()
                .itemSeq(item.getItemSeq())
                .itemName(cleanText(item.getItemName()))
                .entpName(cleanText(item.getEntpName()))
                .drugShape(cleanText(item.getDrugShape()))
                .colorFront(cleanText(item.getColorClass1()))
                .colorBack(cleanText(item.getColorClass2()))
                .formCodeName(cleanText(item.getFormCodeName()))
                .lineFront(cleanText(item.getLineFront()))
                .lineBack(cleanText(item.getLineBack()))
                .printFront(cleanText(item.getPrintFront()))
                .printBack(cleanText(item.getPrintBack()))
                .imageUrl(item.getItemImage())
                .markCodeFront(cleanText(item.getMarkCodeFrontAnal()))
                .markCodeBack(cleanText(item.getMarkCodeBackAnal()))
                .lengLong(parseDouble(item.getLengLong()))
                .lengShort(parseDouble(item.getLengShort()))
                .thick(parseDouble(item.getThick()))
                .className(cleanText(item.getClassName()))
                .classNo(cleanText(item.getClassNo()))
                .etcOtcName(cleanText(item.getEtcOtcName()))
                .build();
    }

    private String cleanText(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        // 앞뒤 공백 제거 및 HTML 엔티티 처리
        return text.trim()
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"");
    }

    private Double parseDouble(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            log.debug("Could not parse double value: {}", value);
            return null;
        }
    }
}
