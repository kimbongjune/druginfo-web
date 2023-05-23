package com.nocdu.druginfo.pillinfo.entity;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 식별정보 엔티티 클래스
 */
@Getter
@Setter
@Data
@Entity
@Table(name = "mdcingrnidntfcinfolist")
public class PillInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    /** 고유 아이디 **/
    private Long id;

    @Column(name = "ITEM_SEQ", insertable = false, updatable = false)
    /** 품목 일련번호 **/
    private String itemSeq;

    @Column(name = "ITEM_NAME")
    /** 품목 명 **/
    private String itemName;

    @Column(name = "ENTP_SEQ")
    /** 업체 일련번호 **/
    private String entpSeq;

    @Column(name = "ENTP_NAME")
    /** 업체 명 **/
    private String entpName;

    @Column(name = "CHART")
    /** 성 상 **/
    private String chart;

    @Column(name = "ITEM_IMAGE")
    /** 큰제품 이미지 **/
    private String itemImage;

    @Column(name = "PRINT_FRONT")
    /** 표시(앞) **/
    private String printFront;

    @Column(name = "PRINT_BACK")
    /** 표시(뒤) **/
    private String printBack;

    @Column(name = "DRUG_SHAPE")
    /** 의약품 모양 **/
    private String drugShape;

    @Column(name = "COLOR_CLASS1")
    /** 색깔(앞) **/
    private String colorClass1;

    @Column(name = "COLOR_CLASS2")
    /** 색깔(뒤) **/
    private String colorClass2;

    @Column(name = "LINE_FRONT")
    /** 분할선(앞) **/
    private String lineFront;

    @Column(name = "LINE_BACK")
    /** 분할선(뒤) **/
    private String lineBack;

    @Column(name = "LENG_LONG")
    /** 크기(장축) **/
    private String lengLong;

    @Column(name = "LENG_SHORT")
    /** 크기(단축) **/
    private String lengShort;

    @Column(name = "THICK")
    /** 크기(두께) **/
    private String thick;

    @Column(name = "IMG_REGIST_TS")
    /** 약학정보원 이미지 생성일 **/
    private String imgRegistTs;

    @Column(name = "CLASS_NO")
    /** 분류번호 **/
    private String classNo;

    @Column(name = "CLASS_NAME")
    /** 분류명 **/
    private String className;

    @Column(name = "ETC_OTC_NAME")
    /** 전문/일반 **/
    private String etcOtcName;

    @Column(name = "ITEM_PERMIT_DATE")
    /** 품목허가일자 **/
    private String itemPermitDate;

    @Column(name = "FORM_CODE_NAME")
    /** 제형코드이름 **/
    private String formCodeName;

    @Column(name = "MARK_CODE_FRONT_ANAL")
    /** 마크내용(앞) **/
    private String markCodeFrontAnal;

    @Column(name = "MARK_CODE_BACK_ANAL")
    /** 마크내용(뒤) **/
    private String markCodeBackAnal;

    @Column(name = "MARK_CODE_FRONT_IMG")
    /** 마크이미지(앞) **/
    private String markCodeFrontImg;

    @Column(name = "MARK_CODE_BACK_IMG")
    /** 마크이미지(뒤) **/
    private String markCodeBackImg;

    @Column(name = "ITEM_ENG_NAME")
    /** 제품영문영 **/
    private String itemEngName;

    @Column(name = "CHANGE_DATE")
    /** 변경일자 **/
    private String changeDate;

    @Column(name = "MARK_CODE_FRONT")
    /** 마크코드(앞) **/
    private String markCodeFront;

    @Column(name = "MARK_CODE_BACK")
    /** 마크코드(뒤) **/
    private String markCodeBack;

    @Column(name = "EDI_CODE")
    /** 보험코드 **/
    private String ediCode;
}