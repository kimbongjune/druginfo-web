package com.nocdu.druginfo.druginfo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nocdu.druginfo.druginfo.dao.DrugInfoDAO;
import com.nocdu.druginfo.druginfo.vo.DrugInfoVO;

/**
 * 	@author 김봉준
 *	의약품 기본정보 데이터베이스 Service 구현 클래스
 */
@Service("drugInfoServiceImpl")
public class DrugInfoServiceImpl implements DrugInfoService {

	/** 의약품 기본정보 DAO **/
    @Resource(name = "DrugInfoDAO")
    private DrugInfoDAO drugInfoDAO;
	
    /**
     * 의약품 기본정보 목록을 전체 조회한다.
     * @param vo DrugInfoVO
     * @return List
     * @exception Exception
     */
	@Override
	public List<?> selectDrugInfoListAll(DrugInfoVO vo) throws Exception {
		return drugInfoDAO.selectDrugInfoListAll(vo);
	}

	/**
     * 의약품 기본정보 목록을 객체를 신규 등록한다.
     * @param vo DrugInfoVO
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertDrugInfoOne(DrugInfoVO vo) throws Exception {
		return drugInfoDAO.insertDrugInfoOne(vo);
	}
	
	/**
     * 의약품 기본정보 목록 리스트 객체를 신규 등록한다.
     * @param DrugInfoList List
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertDrugInfoList(List<?> DrugInfoList) throws Exception {
		return drugInfoDAO.insertDrugInfoList(DrugInfoList);
	}

	/**
     * 의약품 기본정보 목록 특정 객체를 조회한다.
     * @param vo DrugInfoVO
     * @return List
     * @exception Exception
     */
	@Override
	public List<?> selectDrugInfoSearchList(DrugInfoVO vo) throws Exception {
		return drugInfoDAO.selectDrugInfoSearchList(vo);
	}

	/**
     * 의약품 기본정보 목록 특정 객체의 갯수를 조회한다.
     * @param vo DrugInfoVO
     * @return Integer
     * @exception Exception
     */
	@Override
	public int selectDrugInfoSearchListCnt(DrugInfoVO vo) throws Exception {
		return drugInfoDAO.selectDrugInfoSearchListCnt(vo);
	}

}
