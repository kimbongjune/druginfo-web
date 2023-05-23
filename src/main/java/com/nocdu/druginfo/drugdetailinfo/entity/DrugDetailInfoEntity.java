package com.nocdu.druginfo.drugdetailinfo.entity;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 상세정보 엔티티 클래스
 */
@Getter
@Setter
@Data
@Entity
@Table(name = "drugprdtprmsndtlinq")
public class DrugDetailInfoEntity {

    @Id
    @Column(name = "ITEM_SEQ", insertable = false, updatable = false)
    /** 품목기준코드 **/
    private String itemSeq;

    @Column(name = "ITEM_NAME")
    /** 품목명 **/
    private String itemName;

    @Column(name = "ENTP_NAME")
    /** 업체명 **/
    private String entpName;

    @Column(name = "BAR_CODE")
    /** 표준코드 **/
    private String barCode;

    @Column(name = "UD_DOC_ID")
    /** 용법용량 **/
    private String udDocId;

    @Column(name = "STORAGE_METHOD")
    /** 보관방법 **/
    private String storageMethod;

    @Column(name = "VALID_TERM")
    /** 유통기한 **/
    private String validTerm;

    @Column(name = "EDI_CODE")
    /** 보험코드 **/
    private String ediCode;

    @Column(name = "WARNING_TEXT")
    /** 경고문구 **/
    private String warningText;

    @Column(name = "NO_INJECT")
    /** 투여 금지 **/
    private String noInject;

    @Column(name = "CAUTION_INJECT")
    /** 투여 신중 **/
    private String cautionInject;

    @Column(name = "ALLERGY_REACTION")
    /** 이상 반응 **/
    private String allergyReaction;

    @Column(name = "GENERAL_CAUTION")
    /** 일반적 주의사항 **/
    private String generalCaution;

    @Column(name = "MULTIE_INJECT_WARNING")
    /** 복합적 투여 주의사항(임산부, 소아, 노년, 기타) **/
    private String multieInjectWarning;

    @Column(name = "PREGNANT_WOMEN_INJECT_WARNING")
    /** 임산부 투여 주의사항 **/
    private String pregnantWomenInjectWarning;

    @Column(name = "LACTIATION_WOMEN_INJECT_WARNING")
    /** 수유부 투여 주의사항 **/
    private String lactiationWomenInjectWarning;

    @Column(name = "CHILD_INJECT_WARNING")
    /** 소아 투여 주의사항 **/
    private String childInjectWarning;

    @Column(name = "OLDMAN_INJECT_WARNING")
    /** 노년 투여 주의사항 **/
    private String oldmanInjectWarning;

    @Column(name = "PREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING")
    /** 임부 및 수유부 주의사항 **/
    private String pregnantWomenWithLactiationWomenWarning;

    @Column(name = "OVERDOSE_TREATMENT")
    /** 과량 투여시 처치사항 **/
    private String overdoseTreatment;

    @Column(name = "DOSE_CAUTION")
    /** 투여시 주의사항 **/
    private String doseCaution;

    @Column(name = "BEFORE_CONSULT_DOCTOR")
    /** 투여 전 의사와 상담이 필요함 **/
    private String beforeConsultDoctor;

    @Column(name = "AFTER_CONSULT_DOCTOR")
    /** 복용을 중지하고 의사와 상담 **/
    private String afterConsultDoctor;

    @Column(name = "INTERACTION_CAUTION")
    /** 상호작용 **/
    private String interactionCaution;

    @Column(name = "EXTRA_CAUTION")
    /** 기타 주의사항 **/
    private String extraCaution;
}
