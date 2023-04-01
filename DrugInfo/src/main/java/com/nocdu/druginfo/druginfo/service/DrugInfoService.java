package com.nocdu.druginfo.druginfo.service;

import java.util.List;

import com.nocdu.druginfo.druginfo.vo.DrugInfoVO;

public interface DrugInfoService {
    /**
     * 의약품 목록을 전체 조회한다.
     * @param vo DrugInfoVO
     * @return
     * @exception Exception
     */
    public List<?> selectDrugInfoListAll(DrugInfoVO vo) throws Exception;
    
    
    /**
     * 의약품 목록을 객체를 신규 등록한다.
     * @param vo DrugInfoVO
     * @return
     * @exception Exception
     */
    public int insertDrugInfoOne(DrugInfoVO vo) throws Exception;
    
    /**
     * 의약품 목록 리스트 객체를 신규 등록한다.
     * @param DrugInfoList List
     * @return
     * @exception Exception
     */
    public int insertDrugInfoList(List<?> DrugInfoList) throws Exception;
    
    
    /**
     * 의약품 목록 특정 객체를 조회한다.
     * @param vo DrugInfoVO
     * @return
     * @exception Exception
     */
    public List<?> selectDrugInfoSearchList(DrugInfoVO vo) throws Exception;
    
    
    /**
     * 의약품 목록 특정 객체의 갯수를 조회한다.
     * @param vo DrugInfoVO
     * @return
     * @exception Exception
     */
    public int selectDrugInfoSearchListCnt(DrugInfoVO vo) throws Exception;
}
