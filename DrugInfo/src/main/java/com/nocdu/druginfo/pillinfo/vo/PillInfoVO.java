package com.nocdu.druginfo.pillinfo.vo;

public class PillInfoVO {
	
	private static final long serialVersionUID = 6723434363565852261L;
	
	/** 고유 아이디 **/
	private String id;
	
	/** 품목 일련번호 **/
	private String ITEM_SEQ;
	
	/** 품목 명 **/
	private String ITEM_NAME;
	
	/** 업체 일련번호 **/
	private String ENTP_SEQ;
	
	/** 업체 명 **/
	private String ENTP_NAME;
	
	/** 성 상 **/
	private String CHART;
	
	/** 큰제품 이미지 **/
	private String ITEM_IMAGE;
	
	/** 표시(앞) **/
	private String PRINT_FRONT;
	
	/** 표시(뒤) **/
	private String PRINT_BACK;
	
	/** 의약품 모양 **/
	private String DRUG_SHAPE;
	
	/** 색깔(앞) **/
	private String COLOR_CLASS1;
	
	/** 색깔(뒤) **/
	private String COLOR_CLASS2;
	
	/** 분할선(앞) **/
	private String LINE_FRONT;
	
	/** 분할선(뒤) **/
	private String LINE_BACK;
	
	/** 크기(장축) **/
	private String LENG_LONG;
	
	/** 크기(단축) **/
	private String LENG_SHORT;
	
	/** 크기(두께) **/
	private String THICK;
	
	/** 약학정보원 이미지 생성일 **/
	private String IMG_REGIST_TS;
	
	/** 분류번호 **/
	private String CLASS_NO;
	
	/** 분류명 **/
	private String CLASS_NAME;
	
	/** 전문/일반 **/
	private String ETC_OTC_NAME;
	
	/** 품목허가일자 **/
	private String ITEM_PERMIT_DATE;
	
	/** 제형코드이름 **/
	private String FORM_CODE_NAME;
	
	/** 마크내용(앞) **/
	private String MARK_CODE_FRONT_ANAL;
	
	/** 마크내용(뒤) **/
	private String MARK_CODE_BACK_ANAL;
	
	/** 마크이미지(앞) **/
	private String MARK_CODE_FRONT_IMG;
	
	/** 마크이미지(뒤) **/
	private String MARK_CODE_BACK_IMG;
	
	/** 제품영문영 **/
	private String ITEM_ENG_NAME;
	
	/** 변경일자 **/
	private String CHANGE_DATE;
	
	/** 마크코드(앞) **/
	private String MARK_CODE_FRONT;
	
	/** 마크코드(뒤) **/
	private String MARK_CODE_BACK;
	
	/** 보험코드 **/
	private String EDI_CODE;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getENTP_SEQ() {
		return ENTP_SEQ;
	}

	public void setENTP_SEQ(String eNTP_SEQ) {
		ENTP_SEQ = eNTP_SEQ;
	}

	public String getENTP_NAME() {
		return ENTP_NAME;
	}

	public void setENTP_NAME(String eNTP_NAME) {
		ENTP_NAME = eNTP_NAME;
	}

	public String getCHART() {
		return CHART;
	}

	public void setCHART(String cHART) {
		CHART = cHART;
	}

	public String getITEM_IMAGE() {
		return ITEM_IMAGE;
	}

	public void setITEM_IMAGE(String iTEM_IMAGE) {
		ITEM_IMAGE = iTEM_IMAGE;
	}

	public String getPRINT_FRONT() {
		return PRINT_FRONT;
	}

	public void setPRINT_FRONT(String pRINT_FRONT) {
		PRINT_FRONT = pRINT_FRONT;
	}

	public String getPRINT_BACK() {
		return PRINT_BACK;
	}

	public void setPRINT_BACK(String pRINT_BACK) {
		PRINT_BACK = pRINT_BACK;
	}

	public String getDRUG_SHAPE() {
		return DRUG_SHAPE;
	}

	public void setDRUG_SHAPE(String dRUG_SHAPE) {
		DRUG_SHAPE = dRUG_SHAPE;
	}

	public String getCOLOR_CLASS1() {
		return COLOR_CLASS1;
	}

	public void setCOLOR_CLASS1(String cOLOR_CLASS1) {
		COLOR_CLASS1 = cOLOR_CLASS1;
	}

	public String getCOLOR_CLASS2() {
		return COLOR_CLASS2;
	}

	public void setCOLOR_CLASS2(String cOLOR_CLASS2) {
		COLOR_CLASS2 = cOLOR_CLASS2;
	}

	public String getLINE_FRONT() {
		return LINE_FRONT;
	}

	public void setLINE_FRONT(String lINE_FRONT) {
		LINE_FRONT = lINE_FRONT;
	}

	public String getLINE_BACK() {
		return LINE_BACK;
	}

	public void setLINE_BACK(String lINE_BACK) {
		LINE_BACK = lINE_BACK;
	}

	public String getLENG_LONG() {
		return LENG_LONG;
	}

	public void setLENG_LONG(String lENG_LONG) {
		LENG_LONG = lENG_LONG;
	}

	public String getLENG_SHORT() {
		return LENG_SHORT;
	}

	public void setLENG_SHORT(String lENG_SHORT) {
		LENG_SHORT = lENG_SHORT;
	}

	public String getTHICK() {
		return THICK;
	}

	public void setTHICK(String tHICK) {
		THICK = tHICK;
	}

	public String getIMG_REGIST_TS() {
		return IMG_REGIST_TS;
	}

	public void setIMG_REGIST_TS(String iMG_REGIST_TS) {
		IMG_REGIST_TS = iMG_REGIST_TS;
	}

	public String getCLASS_NO() {
		return CLASS_NO;
	}

	public void setCLASS_NO(String cLASS_NO) {
		CLASS_NO = cLASS_NO;
	}

	public String getCLASS_NAME() {
		return CLASS_NAME;
	}

	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}

	public String getETC_OTC_NAME() {
		return ETC_OTC_NAME;
	}

	public void setETC_OTC_NAME(String eTC_OTC_NAME) {
		ETC_OTC_NAME = eTC_OTC_NAME;
	}

	public String getITEM_PERMIT_DATE() {
		return ITEM_PERMIT_DATE;
	}

	public void setITEM_PERMIT_DATE(String iTEM_PERMIT_DATE) {
		ITEM_PERMIT_DATE = iTEM_PERMIT_DATE;
	}

	public String getFORM_CODE_NAME() {
		return FORM_CODE_NAME;
	}

	public void setFORM_CODE_NAME(String fORM_CODE_NAME) {
		FORM_CODE_NAME = fORM_CODE_NAME;
	}

	public String getMARK_CODE_FRONT_ANAL() {
		return MARK_CODE_FRONT_ANAL;
	}

	public void setMARK_CODE_FRONT_ANAL(String mARK_CODE_FRONT_ANAL) {
		MARK_CODE_FRONT_ANAL = mARK_CODE_FRONT_ANAL;
	}

	public String getMARK_CODE_BACK_ANAL() {
		return MARK_CODE_BACK_ANAL;
	}

	public void setMARK_CODE_BACK_ANAL(String mARK_CODE_BACK_ANAL) {
		MARK_CODE_BACK_ANAL = mARK_CODE_BACK_ANAL;
	}

	public String getMARK_CODE_FRONT_IMG() {
		return MARK_CODE_FRONT_IMG;
	}

	public void setMARK_CODE_FRONT_IMG(String mARK_CODE_FRONT_IMG) {
		MARK_CODE_FRONT_IMG = mARK_CODE_FRONT_IMG;
	}

	public String getMARK_CODE_BACK_IMG() {
		return MARK_CODE_BACK_IMG;
	}

	public void setMARK_CODE_BACK_IMG(String mARK_CODE_BACK_IMG) {
		MARK_CODE_BACK_IMG = mARK_CODE_BACK_IMG;
	}

	public String getITEM_ENG_NAME() {
		return ITEM_ENG_NAME;
	}

	public void setITEM_ENG_NAME(String iTEM_ENG_NAME) {
		ITEM_ENG_NAME = iTEM_ENG_NAME;
	}

	public String getCHANGE_DATE() {
		return CHANGE_DATE;
	}

	public void setCHANGE_DATE(String cHANGE_DATE) {
		CHANGE_DATE = cHANGE_DATE;
	}

	public String getMARK_CODE_FRONT() {
		return MARK_CODE_FRONT;
	}

	public void setMARK_CODE_FRONT(String mARK_CODE_FRONT) {
		MARK_CODE_FRONT = mARK_CODE_FRONT;
	}

	public String getMARK_CODE_BACK() {
		return MARK_CODE_BACK;
	}

	public void setMARK_CODE_BACK(String mARK_CODE_BACK) {
		MARK_CODE_BACK = mARK_CODE_BACK;
	}

	public String getEDI_CODE() {
		return EDI_CODE;
	}

	public void setEDI_CODE(String eDI_CODE) {
		EDI_CODE = eDI_CODE;
	}
	
	
}
