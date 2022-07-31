package com.nocdu.druginfo.drugdetailinfo.dao;

import java.util.List;

import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;

public interface DrugDetailInfoDAO {
    /**
     * 의약품 상세 정보를 전체 조회한다.
     * @param vo DrugDetailInfoVO
     * @return
     * @exception Exception
     */
    public List<?> selectDrugDetailInfoListAll(DrugDetailInfoVO vo) throws Exception;
    
    /**
     * 의약품 상세 정보 객체를 신규 등록한다.
     * @param vo DrugDetailInfoVO
     * @return
     * @exception Exception
     */
    public int insertDrugDetailInfoOne(DrugDetailInfoVO vo) throws Exception;
}
