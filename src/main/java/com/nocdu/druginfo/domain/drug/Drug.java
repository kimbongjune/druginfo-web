package com.nocdu.druginfo.domain.drug;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * 의약품 기본정보 엔티티
 * 공공데이터 API: 의약품개요정보(e약은요)
 */
@Entity
@Table(name = "drug", indexes = {
    @Index(name = "idx_drug_item_seq", columnList = "itemSeq", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 품목기준코드 (Unique) */
    @Column(name = "item_seq", length = 50, unique = true)
    private String itemSeq;

    /** 의약품명 */
    @Column(name = "item_name", columnDefinition = "TEXT")
    private String itemName;

    /** 제조사명 */
    @Column(name = "entp_name", length = 500)
    private String entpName;

    /** 효능 */
    @Column(name = "efficacy", columnDefinition = "TEXT")
    private String efficacy;

    /** 복용법 */
    @Column(name = "use_method", columnDefinition = "TEXT")
    private String useMethod;

    /** 주의사항 경고 */
    @Column(name = "caution_warning", columnDefinition = "TEXT")
    private String cautionWarning;

    /** 주의사항 */
    @Column(name = "caution", columnDefinition = "TEXT")
    private String caution;

    /** 상호작용 */
    @Column(name = "interaction", columnDefinition = "TEXT")
    private String interaction;

    /** 부작용 */
    @Column(name = "side_effect", columnDefinition = "TEXT")
    private String sideEffect;

    /** 보관방법 */
    @Column(name = "storage_method", columnDefinition = "TEXT")
    private String storageMethod;

    /** 이미지 URL */
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    /** 공개일 */
    @Column(name = "open_date")
    private LocalDate openDate;

    /** 수정일 */
    @Column(name = "update_date")
    private LocalDate updateDate;

    /** 낱알식별정보 (1:1 관계) */
    @OneToOne(mappedBy = "drug", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DrugIdentification identification;

    /** 안전사용정보 (1:1 관계) */
    @OneToOne(mappedBy = "drug", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DrugSafetyInfo safetyInfo;
}
