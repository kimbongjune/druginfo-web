package com.nocdu.druginfo.pillinfo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nocdu.druginfo.pillinfo.dao.PillInfoDAO;
import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

/**
 * @author 김봉준
 * 의약품 식별정보 데이터베이스 Service 구현 클래스
 */
@Service("pillInfoServiceImpl")
public class PillInfoServiceImpl implements PillInfoService {

	/** 의약품 식별정보 DAO **/
    @Resource(name = "PillInfoDAO")
    private PillInfoDAO pillInfoDAO;

    /**
     * 의약품 식별정보 목록을 전체 조회한다.
     * @param vo DrugInfoVO
     * @return List
     * @exception Exception
     */
	@Override
	public List<?> selectPillInfoListAll(PillInfoVO vo) throws Exception {
		return pillInfoDAO.selectPillInfoListAll(vo);
	}

	/**
     * 의약품 식별정보 목록을 객체를 신규 등록한다.
     * @param vo DrugInfoVO
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertPillInfoOne(PillInfoVO vo) throws Exception {
		return pillInfoDAO.insertPillInfoOne(vo);
	}

	/**
     * 의약품 식별정보 목록리스트 객체를 신규 등록한다.
     * @param pillInfoList List
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertPillInfoList(List<?> pillInfoList) throws Exception {
		return pillInfoDAO.insertPillInfoList(pillInfoList);
	}
	

}
