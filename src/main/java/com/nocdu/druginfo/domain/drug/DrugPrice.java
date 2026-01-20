package com.nocdu.druginfo.domain.drug;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 의약품 가격 정보 엔티티
 * 공공데이터 API: 건강보험심사평가원 약가기준정보
 */
@Entity
@Table(name = "drug_price", indexes = {
    @Index(name = "idx_price_item_seq", columnList = "itemSeq"),
    @Index(name = "idx_price_edi_code", columnList = "ediCode")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugPrice {

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

    /** 제품명 */
    @Column(name = "item_name", columnDefinition = "TEXT")
    private String itemName;

    /** 업체명 */
    @Column(name = "entp_name", length = 500)
    private String entpName;

    /** 보험코드 (EDI코드) */
    @Column(name = "edi_code", length = 50)
    private String ediCode;

    /** 약가 (원) */
    @Column(name = "drug_amount", precision = 15, scale = 2)
    private BigDecimal drugAmount;

    /** 단위 */
    @Column(name = "unit", length = 100)
    private String unit;

    /** 규격 */
    @Column(name = "spec", columnDefinition = "TEXT")
    private String spec;

    /** 적용시작일 */
    @Column(name = "apply_start_date", length = 20)
    private String applyStartDate;

    /** 적용종료일 */
    @Column(name = "apply_end_date", length = 20)
    private String applyEndDate;

    /** 급여구분 (급여/비급여) */
    @Column(name = "pay_type", length = 50)
    private String payType;

    /** 상한가 */
    @Column(name = "max_price", precision = 15, scale = 2)
    private BigDecimal maxPrice;

    /** 일반명코드 (성분약효정보 조회 시 사용) */
    @Column(name = "gnl_nm_cd", length = 50)
    private String gnlNmCd;
}
