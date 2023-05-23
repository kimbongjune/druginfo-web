package com.nocdu.druginfo.druginfo.entity;

import com.nocdu.druginfo.drugdetailinfo.entity.DrugDetailInfoEntity;
import com.nocdu.druginfo.pillinfo.entity.PillInfoEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 기본정보 엔티티 클래스
 */
@Getter
@Setter
@Data
@Entity
@Table(name = "drbeasydruglist")
public class DrugInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    /** 고유 ID **/
    private Long id;

    /** 제약사 명 **/
    @Column(name = "ENTP_NAME")
    private String entpName;

    /** 의약품 명 **/
    @Column(name = "ITEM_NAME")
    private String itemName;

    /** 품목기준코드 **/
    @Column(name = "ITEM_SEQ")
    private String itemSeq;

    /** 의약품 효능 **/
    @Column(name = "EFCY_QESITM")
    private String efcyQesitm;

    /** 의약품 복용법 **/
    @Column(name = "USE_METHOD_QESITM")
    private String useMethodQesitm;

    /** 복용 전 주의사항 **/
    @Column(name = "ATPN_WARN_QUSITM")
    private String atpnWarnQesitm;

    /** 복용 시 주의사항 **/
    @Column(name = "ATPN_QESITM")
    private String atpnQesitm;

    /** 복용 간 주의 약품 및 식품 **/
    @Column(name = "INTRC_QUSITM")
    private String intrcQesitm;

    /** 복용 시 부작용 **/
    @Column(name = "SE_QESITM")
    private String seQesitm;

    /** 보관 방법 **/
    @Column(name = "DEPOSIT_METHOD_QESITM")
    private String depositMethodQesitm;

    /** 공개 일자 **/
    @Column(name = "OPEN_DE")
    private String openDe;

    /** 수정 일자 **/
    @Column(name = "UPDATE_DE")
    private String updateDe;

    /** 의약품 이미지 **/
    @Column(name = "ITEM_IMAGE")
    private String itemImage;
}
