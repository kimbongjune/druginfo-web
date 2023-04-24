package com.nocdu.druginfo.pillinfo.service;

import java.util.List;

import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

/**
 * @author 김봉준
 * 의약품 식별정보 데이터베이스 Service 인터페이스
 */
public interface PillInfoService {
	
    /**
     * 의약품 식별정보 목록을 전체 조회한다.
     * @param vo DrugInfoVO
     * @return List
     * @exception Exception
     */
    public List<?> selectPillInfoListAll(PillInfoVO vo) throws Exception;
    
    
    /**
     * 의약품 식별정보 목록을 객체를 신규 등록한다.
     * @param vo DrugInfoVO
     * @return Integer
     * @exception Exception
     */
    public int insertPillInfoOne(PillInfoVO vo) throws Exception;
    
    /**
     * 의약품 식별정보 목록리스트 객체를 신규 등록한다.
     * @param pillInfoList List
     * @return Integer
     * @exception Exception
     */
    public int insertPillInfoList(List<?> pillInfoList) throws Exception;
}
