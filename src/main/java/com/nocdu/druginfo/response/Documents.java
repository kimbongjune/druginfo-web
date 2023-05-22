package com.nocdu.druginfo.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documents {
    private Long id;
    private String entp_name;
    private String item_name;
    private String item_seq;
    private String efcy_qesitm;
    private String use_method_qesitm;
    private String item_image;
    private String class_name;
    private String warning_text;
    private String no_inject;
    private String caution_inject;
    private String allegy_reaction;
    private String general_caution;
    private String multie_inject_warning;
    private String pregnant_women_inject_warning;
    private String lactation_women_inject_warning;
    private String child_inject_warning;
    private String oldman_inject_warning;
    private String pregnant_women_with_lactation_woman_warning;
    private String overdose_treatment;
    private String dose_caution;
    private String before_consult_doctor;
    private String after_consult_doctor;
    private String interaction_caution;
    private String extra_caution;
    private String storage_method;
    private String valid_term;
    private String edi_code;
}