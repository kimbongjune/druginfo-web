package com.nocdu.druginfo.application.drug;

import com.nocdu.druginfo.api.drug.request.DrugSearchRequest;
import com.nocdu.druginfo.api.drug.request.PillSearchRequest;
import com.nocdu.druginfo.api.drug.response.DrugSearchResponse;
import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.domain.drug.DrugDurWarning;
import com.nocdu.druginfo.domain.drug.DrugInteraction;
import com.nocdu.druginfo.domain.drug.DrugPrice;
import com.nocdu.druginfo.domain.drug.repository.DrugDurWarningRepository;
import com.nocdu.druginfo.domain.drug.repository.DrugInteractionRepository;
import com.nocdu.druginfo.domain.drug.repository.DrugPriceRepository;
import com.nocdu.druginfo.domain.drug.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 의약품 검색 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrugSearchService {

    private final DrugRepository drugRepository;
    private final DrugInteractionRepository drugInteractionRepository;
    private final DrugDurWarningRepository drugDurWarningRepository;
    private final DrugPriceRepository drugPriceRepository;
    private final com.nocdu.druginfo.domain.drug.repository.DrugIngredientRepository drugIngredientRepository;

    // ... (중략) ...

    /**
     * 응답에 추가 정보 (DUR/약가/성분) 포함
     * DUR 경고 정보를 조회하여 리스트 필드와 요약 문자열 필드(pregnantWarning 등) 모두 채움
     */
    private void enrichWithAdditionalInfo(DrugSearchResponse response, String itemSeq) {
        if (itemSeq == null) return;

        // 병용금기 정보
        List<DrugSearchResponse.InteractionInfo> interactions = getInteractionsByItemSeq(itemSeq);
        if (!interactions.isEmpty()) {
            response.setInteractions(interactions);
        }

        // DUR 경고 정보 조회
        List<DrugSearchResponse.DurWarningInfo> durWarnings = getDurWarningsByItemSeq(itemSeq);
        if (!durWarnings.isEmpty()) {
            response.setDurWarnings(durWarnings);
            
            // 앱 호환을 위해 요약 필드(String)도 채워줌 (DUR 데이터 기반)
            fillSafetyInfoFromDur(response, durWarnings);
        }

        // 약가 정보 조회 및 설정
        Optional<DrugSearchResponse.PriceInfo> priceInfoOpt = getPriceByItemSeq(itemSeq);
        priceInfoOpt.ifPresent(response::setPriceInfo);
        
        // 성분 정보 조회 (약가 정보의 gnlNmCd 활용)
        // 약가 정보가 있고 gnlNmCd가 존재해야 성분 정보를 찾을 수 있음
        List<DrugPrice> prices = drugPriceRepository.findByItemSeq(itemSeq);
        if (!prices.isEmpty()) {
            String gnlNmCd = prices.get(0).getGnlNmCd();
            if (gnlNmCd != null && !gnlNmCd.isEmpty()) {
                List<com.nocdu.druginfo.domain.drug.DrugIngredient> ingredients = drugIngredientRepository.findByComponentCode(gnlNmCd);
                if (!ingredients.isEmpty()) {
                    List<DrugSearchResponse.IngredientInfo> ingredientInfos = ingredients.stream()
                            .map(DrugSearchResponse.IngredientInfo::from)
                            .toList();
                    response.setIngredients(ingredientInfos);
                }
            }
        }
    }

    /**
     * DUR 경고 리스트를 분석하여 앱 호환용 안전정보 필드(String) 채우기
     */
    private void fillSafetyInfoFromDur(DrugSearchResponse response, List<DrugSearchResponse.DurWarningInfo> durWarnings) {
        StringBuilder pregnant = new StringBuilder();
        StringBuilder child = new StringBuilder();
        StringBuilder elderly = new StringBuilder();

        for (DrugSearchResponse.DurWarningInfo warning : durWarnings) {
            String content = warning.getTypeName() + ": " + warning.getProhbtContent();
            
            if (warning.getWarningType() != null) {
                switch (warning.getWarningType()) {
                    case "PREGNANT_TABOO":
                        if (pregnant.length() > 0) pregnant.append("\n");
                        pregnant.append(content);
                        break;
                    case "AGE_TABOO":
                        if (child.length() > 0) child.append("\n");
                        child.append(content);
                        break;
                    case "ELDERLY_CAUTION":
                        if (elderly.length() > 0) elderly.append("\n");
                        elderly.append(content);
                        break;
                }
            }
        }

        if (pregnant.length() > 0 && response.getPregnantWarning() == null) {
            response.setPregnantWarning(pregnant.toString());
        }
        if (child.length() > 0 && response.getChildWarning() == null) {
            response.setChildWarning(child.toString());
        }
        if (elderly.length() > 0 && response.getElderlyWarning() == null) {
            response.setElderlyWarning(elderly.toString());
        }
    }
}
