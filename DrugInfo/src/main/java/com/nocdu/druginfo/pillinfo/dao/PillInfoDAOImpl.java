package com.nocdu.druginfo.pillinfo.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

/**
 * @author 김봉준
 * 의약품 식별정보 데이터베이스 DAO 구현 클래스
 */
@Repository(value="PillInfoDAO")
public class PillInfoDAOImpl implements PillInfoDAO {
	
	@Inject
	private SqlSession sql;
	
	//mybatis xml의 namespace
	private static String namespace = "admin";

	/**
     * 의약품 식별정보 목록을 전체 조회한다.
     * @param vo PillInfoVO
     * @return List
     * @exception Exception
     */
	@Override
	public List<?> selectPillInfoListAll(PillInfoVO vo) throws Exception {
		return sql.selectList(namespace+".selectPillInfoListAll");
	}

	/**
     * 의약품 식별정보 목록을 객체를 신규 등록한다.
     * @param vo PillInfoVO
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertPillInfoOne(PillInfoVO vo) throws Exception {
		return sql.insert(namespace+".insertPillInfoOne", vo);
	}

	/**
     * 의약품 식별정보 목록리스트 객체를 신규 등록한다.
     * @param pillInfoList List 
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertPillInfoList(List<?> pillInfoList) throws Exception {
		return sql.insert(namespace+".insertPillInfoList", pillInfoList);
	}
}
