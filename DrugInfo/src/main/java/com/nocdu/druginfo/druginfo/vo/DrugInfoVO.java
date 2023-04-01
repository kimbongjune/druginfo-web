package com.nocdu.druginfo.druginfo.vo;

import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;
import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

public class DrugInfoVO {
	
	private static final long serialVersionUID = 6723434363565852261L;
	
	private DrugDetailInfoVO drugDetailInfoVO;
	
	private PillInfoVO pillInfoVO;
	
	/** 제약사 명 **/
	private String id;
	
	/** 제약사 명 **/
	private String entpName;
	
	/** 의약품 명 **/
	private String itemName;
	
	/** 품목기준코드 **/
	private String itemSeq;
	
	/** 의약품 효능 **/
	private String efcyQesitm;
	
	/** 의약품 복용법 **/
	private String useMethodQesitm;
	
	/** 복용 전 주의사항 **/
	private String atpnWarnQesitm;
	
	/** 복용 시 주의사항 **/
	private String atpnQesitm;
	
	/** 복용 간 주의 약품 및 식품 **/
	private String intrcQesitm;
	
	/** 복용 시 부작용 **/
	private String seQesitm;
	
	/** 보관 방법 **/
	private String depositMethodQesitm;
	
	/** 공개 일자 **/
	private String openDe;
	
	/** 수정 일자 **/
	private String updateDe;
	
	/** 의약품 이미지 **/
	private String itemImage;
	
	/** 검색어 갯수 **/
	private int total;
	
	/** 검색 페이징 **/
	private int page;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntpName() {
		return entpName;
	}

	public void setEntpName(String entpName) {
		this.entpName = entpName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}

	public String getEfcyQesitm() {
		return efcyQesitm;
	}

	public void setEfcyQesitm(String efcyQesitm) {
		this.efcyQesitm = efcyQesitm;
	}

	public String getUseMethodQesitm() {
		return useMethodQesitm;
	}

	public void setUseMethodQesitm(String useMethodQesitm) {
		this.useMethodQesitm = useMethodQesitm;
	}

	public String getAtpnWarnQesitm() {
		return atpnWarnQesitm;
	}

	public void setAtpnWarnQesitm(String atpnWarnQesitm) {
		this.atpnWarnQesitm = atpnWarnQesitm;
	}

	public String getAtpnQesitm() {
		return atpnQesitm;
	}

	public void setAtpnQesitm(String atpnQesitm) {
		this.atpnQesitm = atpnQesitm;
	}

	public String getIntrcQesitm() {
		return intrcQesitm;
	}

	public void setIntrcQesitm(String intrcQesitm) {
		this.intrcQesitm = intrcQesitm;
	}

	public String getSeQesitm() {
		return seQesitm;
	}

	public void setSeQesitm(String seQesitm) {
		this.seQesitm = seQesitm;
	}

	public String getDepositMethodQesitm() {
		return depositMethodQesitm;
	}

	public void setDepositMethodQesitm(String depositMethodQesitm) {
		this.depositMethodQesitm = depositMethodQesitm;
	}

	public String getOpenDe() {
		return openDe;
	}

	public void setOpenDe(String openDe) {
		this.openDe = openDe;
	}

	public String getUpdateDe() {
		return updateDe;
	}

	public void setUpdateDe(String updateDe) {
		this.updateDe = updateDe;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public DrugDetailInfoVO getDrugDetailInfoVO() {
		return drugDetailInfoVO;
	}

	public void setDrugDetailInfoVO(DrugDetailInfoVO drugDetailInfoVO) {
		this.drugDetailInfoVO = drugDetailInfoVO;
	}

	public PillInfoVO getPillInfoVO() {
		return pillInfoVO;
	}

	public void setPillInfoVO(PillInfoVO pillInfoVO) {
		this.pillInfoVO = pillInfoVO;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	
}