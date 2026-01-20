package com.nocdu.druginfo.batch.processor;

import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.infrastructure.api.dto.DrugEasyInfoApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 의약품 데이터 변환 Processor
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugProcessor implements ItemProcessor<DrugEasyInfoApiResponse.Item, Drug> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_FORMATTER_NO_DASH = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public Drug process(DrugEasyInfoApiResponse.Item item) {
        try {
            // 필수 필드 검증
            if (!StringUtils.hasText(item.getItemSeq())) {
                log.warn("Skipping item without itemSeq: {}", item.getItemName());
                return null;
            }

            return Drug.builder()
                    .itemSeq(item.getItemSeq())
                    .itemName(cleanText(item.getItemName()))
                    .entpName(cleanText(item.getEntpName()))
                    .efficacy(cleanHtml(item.getEfcyQesitm()))
                    .useMethod(cleanHtml(item.getUseMethodQesitm()))
                    .cautionWarning(cleanHtml(item.getAtpnWarnQesitm()))
                    .caution(cleanHtml(item.getAtpnQesitm()))
                    .interaction(cleanHtml(item.getIntrcQesitm()))
                    .sideEffect(cleanHtml(item.getSeQesitm()))
                    .storageMethod(cleanText(item.getDepositMethodQesitm()))
                    .imageUrl(item.getItemImage())
                    .openDate(parseDate(item.getOpenDe()))
                    .updateDate(parseDate(item.getUpdateDe()))
                    .build();

        } catch (Exception e) {
            log.error("Error processing item: {} - {}", item.getItemName(), e.getMessage());
            return null;
        }
    }

    /**
     * 날짜 파싱
     */
    private LocalDate parseDate(String dateStr) {
        if (!StringUtils.hasText(dateStr)) {
            return null;
        }
        try {
            // yyyy-MM-dd 형식 시도
            if (dateStr.contains("-")) {
                return LocalDate.parse(dateStr, DATE_FORMATTER);
            }
            // yyyyMMdd 형식 시도
            return LocalDate.parse(dateStr, DATE_FORMATTER_NO_DASH);
        } catch (DateTimeParseException e) {
            log.debug("Could not parse date: {}", dateStr);
            return null;
        }
    }

    /**
     * HTML 태그 제거 및 텍스트 정리
     */
    private String cleanHtml(String html) {
        if (!StringUtils.hasText(html)) {
            return null;
        }
        // HTML 태그 제거
        String text = html.replaceAll("<[^>]*>", " ");
        // 특수 HTML 엔티티 처리
        text = text.replaceAll("&nbsp;", " ")
                   .replaceAll("&lt;", "<")
                   .replaceAll("&gt;", ">")
                   .replaceAll("&amp;", "&")
                   .replaceAll("&quot;", "\"");
        // 연속 공백 정리
        text = text.replaceAll("\\s+", " ").trim();
        return text.isEmpty() ? null : text;
    }

    /**
     * 텍스트 정리
     */
    private String cleanText(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        return text.trim();
    }
}
