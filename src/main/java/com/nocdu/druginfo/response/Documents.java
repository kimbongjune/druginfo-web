package com.nocdu.druginfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 검색 API 응답 객체
 * 의약품 정보를 담고있다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documents {
    /** 고유 아이디 **/
    private Long id;
    /** 업체 일련번호 **/
    private String entp_name;
    /** 품목 명 **/
    private String item_name;
    /** 품목 일련번호 **/
    private String item_seq;
    /** 의약품 효능 **/
    private String efcy_qesitm;
    /** 의약품 복용법 **/
    private String use_method_qesitm;
    /** 의약품 이미지 **/
    private String item_image;
    /** 분류명 **/
    private String class_name;
    /** 경고문구 **/
    private String warning_text;
    /** 투여 금지 **/
    private String no_inject;
    /** 투여 신중 **/
    private String caution_inject;
    /** 이상 반응 **/
    private String allegy_reaction;
    /** 일반적 주의사항 **/
    private String general_caution;
    /** 복합적 투여 주의사항(임산부, 소아, 노년, 기타) **/
    private String multie_inject_warning;
    /** 임산부 투여 주의사항 **/
    private String pregnant_women_inject_warning;
    /** 수유부 투여 주의사항 **/
    private String lactation_women_inject_warning;
    /** 소아 투여 주의사항 **/
    private String child_inject_warning;
    /** 노년 투여 주의사항 **/
    private String oldman_inject_warning;
    /** 임부 및 수유부 주의사항 **/
    private String pregnant_women_with_lactation_woman_warning;
    /** 과량 투여시 처치사항 **/
    private String overdose_treatment;
    /** 투여시 주의사항 **/
    private String dose_caution;
    /** 투여 전 의사와 상담이 필요함 **/
    private String before_consult_doctor;
    /** 복용을 중지하고 의사와 상담 **/
    private String after_consult_doctor;
    /** 상호작용 **/
    private String interaction_caution;
    /** 기타 주의사항 **/
    private String extra_caution;
    /** 보관방법 **/
    private String storage_method;
    /** 유통기한 **/
    private String valid_term;
    /** 보험코드 **/
    private String edi_code;
}