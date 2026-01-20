package com.nocdu.druginfo.domain.drug;

import jakarta.persistence.*;
import lombok.*;

/**
 * 의약품 낱알식별정보 엔티티
 * 공공데이터 API: 의약품 낱알식별 정보
 */
@Entity
@Table(name = "drug_identification", indexes = {
    @Index(name = "idx_ident_item_seq", columnList = "itemSeq"),
    @Index(name = "idx_ident_drug_shape", columnList = "drugShape"),
    @Index(name = "idx_ident_color_front", columnList = "colorFront"),
    @Index(name = "idx_ident_form_code_name", columnList = "formCodeName")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugIdentification {

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

    /** 품목명 */
    @Column(name = "item_name", columnDefinition = "TEXT")
    private String itemName;

    /** 업체명 */
    @Column(name = "entp_name", length = 500)
    private String entpName;

    /** 모양 (원형, 타원형, 장방형 등) */
    @Column(name = "drug_shape", length = 100)
    private String drugShape;

    /** 앞면 색상 */
    @Column(name = "color_front", length = 100)
    private String colorFront;

    /** 뒷면 색상 */
    @Column(name = "color_back", length = 100)
    private String colorBack;

    /** 앞면 식별문구 */
    @Column(name = "print_front", columnDefinition = "TEXT")
    private String printFront;

    /** 뒷면 식별문구 */
    @Column(name = "print_back", columnDefinition = "TEXT")
    private String printBack;

    /** 앞면 분할선 */
    @Column(name = "line_front", length = 100)
    private String lineFront;

    /** 뒷면 분할선 */
    @Column(name = "line_back", length = 100)
    private String lineBack;

    /** 장축 크기(mm) */
    @Column(name = "leng_long")
    private Double lengLong;

    /** 단축 크기(mm) */
    @Column(name = "leng_short")
    private Double lengShort;

    /** 두께(mm) */
    @Column(name = "thick")
    private Double thick;

    /** 이미지 URL */
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    /** 제형 (정제, 캡슐 등) */
    @Column(name = "form_code_name", length = 200)
    private String formCodeName;

    /** 분류번호 (약효분류번호) */
    @Column(name = "class_no", length = 50)
    private String classNo;

    /** 분류명 */
    @Column(name = "class_name", columnDefinition = "TEXT")
    private String className;

    /** 전문/일반 (전문의약품, 일반의약품) */
    @Column(name = "etc_otc_name", length = 100)
    private String etcOtcName;

    /** 마크코드(앞) */
    @Column(name = "mark_code_front", columnDefinition = "TEXT")
    private String markCodeFront;

    /** 마크코드(뒤) */
    @Column(name = "mark_code_back", columnDefinition = "TEXT")
    private String markCodeBack;
}
