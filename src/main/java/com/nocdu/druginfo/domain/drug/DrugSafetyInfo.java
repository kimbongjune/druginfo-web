package com.nocdu.druginfo.domain.drug;

import jakarta.persistence.*;
import lombok.*;

/**
 * 의약품 안전사용정보 엔티티
 * 공공데이터 API: DUR 품목정보/성분정보
 */
@Entity
@Table(name = "drug_safety_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugSafetyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 의약품 (FK) */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id", unique = true)
    private Drug drug;

    /** 품목기준코드 (검색용) */
    @Column(name = "item_seq", length = 50)
    private String itemSeq;

    /** 경고문구 */
    @Column(name = "warning_text", columnDefinition = "TEXT")
    private String warningText;

    /** 투여금지 */
    @Column(name = "no_inject", columnDefinition = "TEXT")
    private String noInject;

    /** 투여신중 */
    @Column(name = "caution_inject", columnDefinition = "TEXT")
    private String cautionInject;

    /** 이상반응 */
    @Column(name = "allergy_reaction", columnDefinition = "TEXT")
    private String allergyReaction;

    /** 일반적 주의사항 */
    @Column(name = "general_caution", columnDefinition = "TEXT")
    private String generalCaution;

    /** 임산부 투여 주의사항 */
    @Column(name = "pregnant_warning", columnDefinition = "TEXT")
    private String pregnantWarning;

    /** 수유부 투여 주의사항 */
    @Column(name = "lactation_warning", columnDefinition = "TEXT")
    private String lactationWarning;

    /** 소아 투여 주의사항 */
    @Column(name = "child_warning", columnDefinition = "TEXT")
    private String childWarning;

    /** 노인 투여 주의사항 */
    @Column(name = "elderly_warning", columnDefinition = "TEXT")
    private String elderlyWarning;

    /** 과량투여시 처치사항 */
    @Column(name = "overdose_warning", columnDefinition = "TEXT")
    private String overdoseWarning;

    /** 투여시 주의사항 */
    @Column(name = "dose_warning", columnDefinition = "TEXT")
    private String doseWarning;

    /** 투여기간 주의 */
    @Column(name = "duration_warning", columnDefinition = "TEXT")
    private String durationWarning;

    /** 상호작용 주의 */
    @Column(name = "interaction_caution", columnDefinition = "TEXT")
    private String interactionCaution;

    /** 기타 주의사항 */
    @Column(name = "extra_caution", columnDefinition = "TEXT")
    private String extraCaution;

    /** 투여 전 의사 상담 필요 */
    @Column(name = "before_consult_doctor", columnDefinition = "TEXT")
    private String beforeConsultDoctor;

    /** 복용 중지 후 의사 상담 */
    @Column(name = "after_consult_doctor", columnDefinition = "TEXT")
    private String afterConsultDoctor;

    /** 보관방법 */
    @Column(name = "storage_method", columnDefinition = "TEXT")
    private String storageMethod;

    /** 유통기한 */
    @Column(name = "valid_term", length = 500)
    private String validTerm;
}
