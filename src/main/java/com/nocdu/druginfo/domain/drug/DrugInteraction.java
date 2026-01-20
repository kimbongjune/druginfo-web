package com.nocdu.druginfo.domain.drug;

import jakarta.persistence.*;
import lombok.*;

/**
 * 의약품 병용금기(상호작용) 정보 엔티티
 * 공공데이터 API: DUR 품목정보 - 병용금기
 * 하나의 약품이 여러 병용금기 약품을 가질 수 있으므로 ManyToOne 관계
 */
@Entity
@Table(name = "drug_interaction", indexes = {
    @Index(name = "idx_interaction_item_seq", columnList = "itemSeq"),
    @Index(name = "idx_interaction_mixture_seq", columnList = "mixtureItemSeq")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugInteraction {

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

    /** 병용금기 품목기준코드 */
    @Column(name = "mixture_item_seq", length = 50)
    private String mixtureItemSeq;

    /** 병용금기 품목명 */
    @Column(name = "mixture_item_name", columnDefinition = "TEXT")
    private String mixtureItemName;

    /** 병용금기 업체명 */
    @Column(name = "mixture_entp_name", length = 500)
    private String mixtureEntpName;

    /** 병용금기 주성분코드 */
    @Column(name = "mixture_main_ingr_code", columnDefinition = "TEXT")
    private String mixtureMainIngrCode;

    /** 병용금기 주성분명 */
    @Column(name = "mixture_main_ingr", columnDefinition = "TEXT")
    private String mixtureMainIngr;

    /** 금기등급/내용 */
    @Column(name = "prohbt_content", columnDefinition = "TEXT")
    private String prohbtContent;

    /** 금기사유 */
    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

    /** DUR유형코드 */
    @Column(name = "type_code", length = 50)
    private String typeCode;

    /** DUR유형명 */
    @Column(name = "type_name", length = 200)
    private String typeName;
}
