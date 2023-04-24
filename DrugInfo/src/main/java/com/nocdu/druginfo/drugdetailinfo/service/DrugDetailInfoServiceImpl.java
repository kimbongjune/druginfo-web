package com.nocdu.druginfo.drugdetailinfo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nocdu.druginfo.drugdetailinfo.dao.DrugDetailInfoDAO;
import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;

/**
 * 	@author 김봉준
 *	의약품 상세정보 데이터베이스 Service 구현 클래스
 */
@Service("drugDetailInfoServiceImpl")
public class DrugDetailInfoServiceImpl implements DrugDetailInfoService {

    /** 의약품 상세정보 DAO **/
    @Resource(name = "DrugDetailInfoDAO")
    private DrugDetailInfoDAO DrugDetailInfoDAO;
    
    /**
     * 의약품 상세 정보를 전체 조회한다.
     * @param vo DrugDetailInfoVO
     * @return List
     * @exception Exception
     */
	@Override
	public List<?> selectDrugDetailInfoListAll(DrugDetailInfoVO vo) throws Exception {
		return DrugDetailInfoDAO.selectDrugDetailInfoListAll(vo);
	}

	/**
     * 의약품 상세 정보 객체를 신규 등록한다.
     * @param vo DrugDetailInfoVO
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertDrugDetailInfoOne(DrugDetailInfoVO vo) throws Exception {
		return DrugDetailInfoDAO.insertDrugDetailInfoOne(vo);
	}

	/**
     * 의약품 상세 정보리스트 객체를 신규 등록한다.
     * @param drugDetailInfoList List
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertDrugDetailInfoList(List<?> drugDetailInfoList) throws Exception {
		return DrugDetailInfoDAO.insertDrugDetailInfoList(drugDetailInfoList);
	}


}
