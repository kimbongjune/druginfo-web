package com.nocdu.druginfo.drugdetailinfo.vo;

public class DrugDetailInfoVO {
	
	private static final long serialVersionUID = 6723434363565852261L;
	
	/** 품목기준코드 **/
	private String ITEM_SEQ;
	
	/** 품목명 **/
	private String ITEM_NAME;
	
	/** 업체명 **/
	private String ENTP_NAME;
	
	/** 표준코드 **/
	private String BAR_CODE;
	
	/** 용법용량 **/
	private String UD_DOC_ID;
	
	/** 보관방법 **/
	private String STORAGE_METHOD;
	
	/** 유통기한 **/
	private String VALID_TERM;
	
	/** 보험코드 **/
	private String EDI_CODE;
	
	/** 경고문구 **/
	private String WARNING_TEXT;
	
	/** 투여 금지 **/
	private String NO_INJECT;
	
	/** 투여 신중 **/
	private String CAUTION_INJECT;
	
	/** 이상 반응 **/
	private String ALLERGY_REACTION;
	
	/** 일반적 주의사항 **/
	private String GENERAL_CAUTION;
	
	/** 복합적 투여 주의사항(임산부, 소아, 노년, 기타) **/
	private String MULTIE_INJECT_WARNING;
	
	/** 임산부 투여 주의사항 **/
	private String PREGNANT_WOMEN_INJECT_WARNING;
	
	/** 수유부 투여 주의사항 **/
	private String LACTIATION_WOMEN_INJECT_WARNING;
	
	/** 소아 투여 주의사항 **/
	private String CHILD_INJECT_WARNING;
	
	/** 노년 투여 주의사항 **/
	private String OLDMAN_INJECT_WARNING;
	
	/** 임부 및 수유부 주의사항 **/
	private String PREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING;
	
	/** 과량 투여시 처치사항 **/
	private String OVERDOSE_TREATMENT;
	
	/** 투여시 주의사항 **/
	private String DOSE_CAUTION;
	
	/** 투여 전 의사와 상담이 필요함 **/
	private String BEFORE_CONSULT_DOCTOR;
	
	/** 복용을 중지하고 의사와 상담 **/
	private String AFTER_CONSULT_DOCTOR;
	
	/** 상호작용 **/
	private String INTERACTION_CAUTION;
	
	/** 포함되지 않는 텍스트 **/
	private String EXTRA_CAUTION;
	
	public String getITEM_SEQ() {
		return ITEM_SEQ;
	}

	public void setITEM_SEQ(String iTEM_SEQ) {
		ITEM_SEQ = iTEM_SEQ;
	}

	public String getITEM_NAME() {
		return ITEM_NAME;
	}

	public void setITEM_NAME(String iTEM_NAME) {
		ITEM_NAME = iTEM_NAME;
	}

	public String getENTP_NAME() {
		return ENTP_NAME;
	}

	public void setENTP_NAME(String eNTP_NAME) {
		ENTP_NAME = eNTP_NAME;
	}

	public String getBAR_CODE() {
		return BAR_CODE;
	}

	public void setBAR_CODE(String bAR_CODE) {
		BAR_CODE = bAR_CODE;
	}

	public String getUD_DOC_ID() {
		return UD_DOC_ID;
	}

	public void setUD_DOC_ID(String uD_DOC_ID) {
		UD_DOC_ID = uD_DOC_ID;
	}

	public String getSTORAGE_METHOD() {
		return STORAGE_METHOD;
	}

	public void setSTORAGE_METHOD(String sTORAGE_METHOD) {
		STORAGE_METHOD = sTORAGE_METHOD;
	}

	public String getVALID_TERM() {
		return VALID_TERM;
	}

	public void setVALID_TERM(String vALID_TERM) {
		VALID_TERM = vALID_TERM;
	}

	public String getEDI_CODE() {
		return EDI_CODE;
	}

	public void setEDI_CODE(String eDI_CODE) {
		EDI_CODE = eDI_CODE;
	}

	public String getWARNING_TEXT() {
		return WARNING_TEXT;
	}

	public void setWARNING_TEXT(String wARNING_TEXT) {
		WARNING_TEXT = wARNING_TEXT;
	}

	public String getNO_INJECT() {
		return NO_INJECT;
	}

	public void setNO_INJECT(String nO_INJECT) {
		NO_INJECT = nO_INJECT;
	}

	public String getCAUTION_INJECT() {
		return CAUTION_INJECT;
	}

	public void setCAUTION_INJECT(String cAUTION_INJECT) {
		CAUTION_INJECT = cAUTION_INJECT;
	}

	public String getALLERGY_REACTION() {
		return ALLERGY_REACTION;
	}

	public void setALLERGY_REACTION(String aLLERGY_REACTION) {
		ALLERGY_REACTION = aLLERGY_REACTION;
	}

	public String getGENERAL_CAUTION() {
		return GENERAL_CAUTION;
	}

	public void setGENERAL_CAUTION(String gENERAL_CAUTION) {
		GENERAL_CAUTION = gENERAL_CAUTION;
	}

	public String getMULTIE_INJECT_WARNING() {
		return MULTIE_INJECT_WARNING;
	}

	public void setMULTIE_INJECT_WARNING(String mULTIE_INJECT_WARNING) {
		MULTIE_INJECT_WARNING = mULTIE_INJECT_WARNING;
	}

	public String getPREGNANT_WOMEN_INJECT_WARNING() {
		return PREGNANT_WOMEN_INJECT_WARNING;
	}

	public void setPREGNANT_WOMEN_INJECT_WARNING(String pREGNANT_WOMEN_INJECT_WARNING) {
		PREGNANT_WOMEN_INJECT_WARNING = pREGNANT_WOMEN_INJECT_WARNING;
	}
	
	public String getLACTIATION_WOMEN_INJECT_WARNING() {
		return LACTIATION_WOMEN_INJECT_WARNING;
	}

	public void setLACTIATION_WOMEN_INJECT_WARNING(String lACTIATION_WOMEN_INJECT_WARNING) {
		LACTIATION_WOMEN_INJECT_WARNING = lACTIATION_WOMEN_INJECT_WARNING;
	}

	public String getCHILD_INJECT_WARNING() {
		return CHILD_INJECT_WARNING;
	}

	public void setCHILD_INJECT_WARNING(String cHILD_INJECT_WARNING) {
		CHILD_INJECT_WARNING = cHILD_INJECT_WARNING;
	}

	public String getOLDMAN_INJECT_WARNING() {
		return OLDMAN_INJECT_WARNING;
	}

	public void setOLDMAN_INJECT_WARNING(String oLDMAN_INJECT_WARNING) {
		OLDMAN_INJECT_WARNING = oLDMAN_INJECT_WARNING;
	}

	public String getPREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING() {
		return PREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING;
	}

	public void setPREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING(String pREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING) {
		PREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING = pREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING;
	}

	public String getOVERDOSE_TREATMENT() {
		return OVERDOSE_TREATMENT;
	}

	public void setOVERDOSE_TREATMENT(String oVERDOSE_TREATMENT) {
		OVERDOSE_TREATMENT = oVERDOSE_TREATMENT;
	}

	public String getDOSE_CAUTION() {
		return DOSE_CAUTION;
	}

	public void setDOSE_CAUTION(String dOSE_CAUTION) {
		DOSE_CAUTION = dOSE_CAUTION;
	}

	public String getBEFORE_CONSULT_DOCTOR() {
		return BEFORE_CONSULT_DOCTOR;
	}

	public void setBEFORE_CONSULT_DOCTOR(String bEFORE_CONSULT_DOCTOR) {
		BEFORE_CONSULT_DOCTOR = bEFORE_CONSULT_DOCTOR;
	}

	public String getAFTER_CONSULT_DOCTOR() {
		return AFTER_CONSULT_DOCTOR;
	}

	public void setAFTER_CONSULT_DOCTOR(String aFTER_CONSULT_DOCTOR) {
		AFTER_CONSULT_DOCTOR = aFTER_CONSULT_DOCTOR;
	}
	
	public String getINTERACTION_CAUTION() {
		return INTERACTION_CAUTION;
	}

	public void setINTERACTION_CAUTION(String iNTERACTION_CAUTION) {
		INTERACTION_CAUTION = iNTERACTION_CAUTION;
	}

	public String getEXTRA_CAUTION() {
		return EXTRA_CAUTION;
	}

	public void setEXTRA_CAUTION(String eXTRA_CAUTION) {
		EXTRA_CAUTION = eXTRA_CAUTION;
	}
	
	
	
}
