package com.nocdu.druginfo.drugdetailinfo.service;

import java.util.List;

import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;

/**
 * 	@author 김봉준
 *	의약품 상세정보 데이터베이스 Service 인터페이스
 */
public interface DrugDetailInfoService {
    /**
     * 의약품 상세 정보를 전체 조회한다.
     * @param vo DrugDetailInfoVO
     * @return List
     * @exception Exception
     */
    public List<?> selectDrugDetailInfoListAll(DrugDetailInfoVO vo) throws Exception;
    
    /**
     * 의약품 상세 정보 객체를 신규 등록한다.
     * @param vo DrugDetailInfoVO
     * @return Integer
     * @exception Exception
     */
    public int insertDrugDetailInfoOne(DrugDetailInfoVO vo) throws Exception;
    
    /**
     * 의약품 상세 정보리스트 객체를 신규 등록한다.
     * @param drugDetailInfoList List
     * @return Integer
     * @exception Exception
     */
    public int insertDrugDetailInfoList(List<?> drugDetailInfoList) throws Exception;
}
