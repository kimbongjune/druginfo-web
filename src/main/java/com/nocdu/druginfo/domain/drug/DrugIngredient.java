package com.nocdu.druginfo.domain.drug;

import jakarta.persistence.*;
import lombok.*;

/**
 * 의약품 성분/약효 정보 엔티티
 * 공공데이터 API: 건강보험심사평가원 의약품성분약효정보
 */
@Entity
@Table(name = "drug_ingredient", indexes = {
    @Index(name = "idx_ingredient_code", columnList = "componentCode"),
    @Index(name = "idx_ingredient_atc", columnList = "atcCode")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 주성분코드 */
    @Column(name = "component_code", length = 50)
    private String componentCode;

    /** 주성분명 (한글) */
    @Column(name = "component_kor_name", columnDefinition = "TEXT")
    private String componentKorName;

    /** 주성분명 (영문) */
    @Column(name = "component_eng_name", columnDefinition = "TEXT")
    private String componentEngName;

    /** 약효분류코드 */
    @Column(name = "formula_code", length = 50)
    private String formulaCode;

    /** 약효분류명 */
    @Column(name = "formula_name", columnDefinition = "TEXT")
    private String formulaName;

    /** 투여경로 */
    @Column(name = "adm_route", length = 200)
    private String admRoute;

    /** ATC코드 */
    @Column(name = "atc_code", length = 50)
    private String atcCode;

    /** ATC명칭 */
    @Column(name = "atc_name", columnDefinition = "TEXT")
    private String atcName;
}
