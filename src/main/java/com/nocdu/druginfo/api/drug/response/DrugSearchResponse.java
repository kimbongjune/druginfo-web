package com.nocdu.druginfo.api.drug.response;

import com.nocdu.druginfo.domain.drug.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 의약품 검색 결과 응답 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder
public class DrugSearchResponse {

    // 기본 정보
    private Long id;
    private String itemSeq;
    private String itemName;
    private String entpName;
    private String efficacy;
    private String useMethod;
    private String imageUrl;

    // 식별 정보
    private String drugShape;
    private String colorFront;
    private String colorBack;
    private String formCodeName;
    private String className;
    private String etcOtcName;
    private String printFront;
    private String printBack;

    // 안전 정보 (요약)
    private String cautionWarning;
    private String sideEffect;
    private String interaction;
    private String storageMethod;

    // 상세 안전 정보 (앱 호환용 필드명 유지)
    private String pregnantWarning;
    private String lactationWarning;
    private String childWarning;
    private String elderlyWarning;
    private String overdoseWarning;

    // 추가 안전 정보 (앱 기존 필드 호환)
    private String noInject;            // 투여금지
    private String cautionInject;       // 투여신중
    private String generalCaution;      // 일반적 주의사항
    private String extraCaution;        // 기타 주의사항
    private String beforeConsultDoctor; // 투여 전 의사 상담
    private String afterConsultDoctor;  // 복용 중지 후 의사 상담
    private String doseWarning;         // 투여시 주의사항
    private String validTerm;           // 유통기한
    private String ediCode;             // 보험코드 (약가)

    // === 추가된 필드: DUR/약가/성분 정보 ===

    /** DUR 병용금기 정보 목록 */
    private List<InteractionInfo> interactions;

    /** DUR 경고 정보 목록 (연령금기, 임부금기, 노인주의 등) */
    private List<DurWarningInfo> durWarnings;

    /** 약가 정보 */
    private PriceInfo priceInfo;

    // === 내부 DTO 클래스 ===

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InteractionInfo {
        private String mixtureItemSeq;
        private String mixtureItemName;
        private String mixtureEntpName;
        private String mixtureMainIngr;
        private String prohbtContent;
        private String typeCode;
        private String typeName;

        public static InteractionInfo from(DrugInteraction entity) {
            return InteractionInfo.builder()
                    .mixtureItemSeq(entity.getMixtureItemSeq())
                    .mixtureItemName(entity.getMixtureItemName())
                    .mixtureEntpName(entity.getMixtureEntpName())
                    .mixtureMainIngr(entity.getMixtureMainIngr())
                    .prohbtContent(entity.getProhbtContent())
                    .typeCode(entity.getTypeCode())
                    .typeName(entity.getTypeName())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DurWarningInfo {
        private String warningType;
        private String typeCode;
        private String typeName;
        private String prohbtContent;
        private String remark;

        public static DurWarningInfo from(DrugDurWarning entity) {
            return DurWarningInfo.builder()
                    .warningType(entity.getWarningType() != null ? entity.getWarningType().name() : null)
                    .typeCode(entity.getTypeCode())
                    .typeName(entity.getTypeName())
                    .prohbtContent(entity.getProhbtContent())
                    .remark(entity.getRemark())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PriceInfo {
        private String ediCode;
        private BigDecimal drugAmount;
        private BigDecimal maxPrice;
        private String unit;
        private String spec;
        private String payType;
        private String applyStartDate;
        private String applyEndDate;

        public static PriceInfo from(DrugPrice entity) {
            return PriceInfo.builder()
                    .ediCode(entity.getEdiCode())
                    .drugAmount(entity.getDrugAmount())
                    .maxPrice(entity.getMaxPrice())
                    .unit(entity.getUnit())
                    .spec(entity.getSpec())
                    .payType(entity.getPayType())
                    .applyStartDate(entity.getApplyStartDate())
                    .applyEndDate(entity.getApplyEndDate())
                    .build();
        }
    }

    /** 성분 정보 목록 */
    private List<IngredientInfo> ingredients;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IngredientInfo {
        private String componentCode;
        private String componentKorName;
        private String componentEngName;
        private String formulaCode;
        private String formulaName;
        private String admRoute;
        private String atcCode;
        private String atcName;
        
        public static IngredientInfo from(DrugIngredient entity) {
            return IngredientInfo.builder()
                    .componentCode(entity.getComponentCode())
                    .componentKorName(entity.getComponentKorName())
                    .componentEngName(entity.getComponentEngName())
                    .formulaCode(entity.getFormulaCode())
                    .formulaName(entity.getFormulaName())
                    .admRoute(entity.getAdmRoute())
                    .atcCode(entity.getAtcCode())
                    .atcName(entity.getAtcName())
                    .build();
        }
    }

    /**
     * 엔티티에서 응답 DTO 생성 (기본 정보만)
     */
    public static DrugSearchResponse from(Drug drug) {
        DrugSearchResponseBuilder builder = DrugSearchResponse.builder()
                .id(drug.getId())
                .itemSeq(drug.getItemSeq())
                .itemName(drug.getItemName())
                .entpName(drug.getEntpName())
                .efficacy(drug.getEfficacy())
                .useMethod(drug.getUseMethod())
                .imageUrl(drug.getImageUrl())
                .cautionWarning(drug.getCautionWarning())
                .sideEffect(drug.getSideEffect())
                .interaction(drug.getInteraction())
                .storageMethod(drug.getStorageMethod());

        // 식별 정보 추가
        DrugIdentification ident = drug.getIdentification();
        if (ident != null) {
            builder.drugShape(ident.getDrugShape())
                    .colorFront(ident.getColorFront())
                    .colorBack(ident.getColorBack())
                    .formCodeName(ident.getFormCodeName())
                    .className(ident.getClassName())
                    .etcOtcName(ident.getEtcOtcName())
                    .printFront(ident.getPrintFront())
                    .printBack(ident.getPrintBack());
            
            // 이미지 URL이 없으면 식별정보 이미지 사용
            if (drug.getImageUrl() == null && ident.getImageUrl() != null) {
                builder.imageUrl(ident.getImageUrl());
            }
        }

        // 안전 정보 추가
        DrugSafetyInfo safety = drug.getSafetyInfo();
        if (safety != null) {
            builder.pregnantWarning(safety.getPregnantWarning())
                    .lactationWarning(safety.getLactationWarning())
                    .childWarning(safety.getChildWarning())
                    .elderlyWarning(safety.getElderlyWarning())
                    .overdoseWarning(safety.getOverdoseWarning())
                    .noInject(safety.getNoInject())
                    .cautionInject(safety.getCautionInject())
                    .generalCaution(safety.getGeneralCaution())
                    .extraCaution(safety.getExtraCaution())
                    .beforeConsultDoctor(safety.getBeforeConsultDoctor())
                    .afterConsultDoctor(safety.getAfterConsultDoctor())
                    .doseWarning(safety.getDoseWarning())
                    .validTerm(safety.getValidTerm());
        }

        return builder.build();
    }

    /**
     * QueryDSL Projection용 생성자 (기본 필드만)
     */
    public DrugSearchResponse(Long id, String itemSeq, String itemName, String entpName,
                               String efficacy, String useMethod, String imageUrl,
                               String drugShape, String colorFront, String colorBack,
                               String formCodeName, String className, String etcOtcName,
                               String printFront, String printBack,
                               String cautionWarning, String sideEffect, String interaction,
                               String storageMethod,
                               String pregnantWarning, String lactationWarning,
                               String childWarning, String elderlyWarning, String overdoseWarning) {
        this.id = id;
        this.itemSeq = itemSeq;
        this.itemName = itemName;
        this.entpName = entpName;
        this.efficacy = efficacy;
        this.useMethod = useMethod;
        this.imageUrl = imageUrl;
        this.drugShape = drugShape;
        this.colorFront = colorFront;
        this.colorBack = colorBack;
        this.formCodeName = formCodeName;
        this.className = className;
        this.etcOtcName = etcOtcName;
        this.printFront = printFront;
        this.printBack = printBack;
        this.cautionWarning = cautionWarning;
        this.sideEffect = sideEffect;
        this.interaction = interaction;
        this.storageMethod = storageMethod;
        this.pregnantWarning = pregnantWarning;
        this.lactationWarning = lactationWarning;
        this.childWarning = childWarning;
        this.elderlyWarning = elderlyWarning;
        this.overdoseWarning = overdoseWarning;
    }
}
