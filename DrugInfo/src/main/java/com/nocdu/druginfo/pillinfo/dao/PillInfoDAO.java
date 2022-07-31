package com.nocdu.druginfo.pillinfo.dao;

import java.util.List;

import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;
public interface PillInfoDAO {
    /**
     * 의약품 목록을 전체 조회한다.
     * @param vo DrugInfoVO
     * @return
     * @exception Exception
     */
    public List<?> selectPillInfoListAll(PillInfoVO vo) throws Exception;
    
    /**
     * 의약품 목록을 객체를 신규 등록한다.
     * @param vo DrugInfoVO
     * @return
     * @exception Exception
     */
    public int insertPillInfoOne(PillInfoVO vo) throws Exception;
}
