package com.nocdu.druginfo.domain.drug;

import jakarta.persistence.*;
import lombok.*;

/**
 * 의약품 DUR 경고 정보 엔티티
 * 연령금기, 임부금기, 노인주의 등 DUR 경고 정보 통합 저장
 */
@Entity
@Table(name = "drug_dur_warning", indexes = {
    @Index(name = "idx_dur_warning_item_seq", columnList = "itemSeq"),
    @Index(name = "idx_dur_warning_type", columnList = "warningType")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugDurWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 의약품 (FK) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;

    /** 품목기준코드 */
    @Column(name = "item_seq", length = 50)
    private String itemSeq;

    /** 품목명 */
    @Column(name = "item_name", columnDefinition = "TEXT")
    private String itemName;

    /** 업체명 */
    @Column(name = "entp_name", length = 500)
    private String entpName;

    /** 주성분코드 */
    @Column(name = "main_ingr_code", columnDefinition = "TEXT")
    private String mainIngrCode;

    /** 주성분명 (한글) */
    @Column(name = "main_ingr_kor", columnDefinition = "TEXT")
    private String mainIngrKor;

    /** 경고 유형 (AGE_TABOO, PREGNANT_TABOO, ELDERLY_CAUTION) */
    @Column(name = "warning_type", length = 30)
    @Enumerated(EnumType.STRING)
    private WarningType warningType;

    /** 금기/주의 내용 */
    @Column(name = "prohbt_content", columnDefinition = "TEXT")
    private String prohbtContent;

    /** 비고/사유 */
    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

    /** DUR유형코드 */
    @Column(name = "type_code", length = 50)
    private String typeCode;

    /** DUR유형명 */
    @Column(name = "type_name", length = 200)
    private String typeName;

    public enum WarningType {
        AGE_TABOO,          // 특정연령대금기
        PREGNANT_TABOO,     // 임부금기
        ELDERLY_CAUTION,    // 노인주의
        DUPLICATE_EFFECT    // 효능군중복
    }
}
