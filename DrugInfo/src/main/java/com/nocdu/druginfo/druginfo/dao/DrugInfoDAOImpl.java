package com.nocdu.druginfo.druginfo.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.nocdu.druginfo.druginfo.vo.DrugInfoVO;

/**
 * 	@author 김봉준
 *	의약품 기본정보 데이터베이스 DAO 인터페이스
 */
@Repository(value="DrugInfoDAO")
public class DrugInfoDAOImpl implements DrugInfoDAO {
	
	@Inject
	private SqlSession sql;
	
	//mybatis xml의 namespace
	private static String namespace = "admin";

	/**
     * 의약품 기본정보 목록을 전체 조회한다.
     * @param vo DrugInfoVO
     * @return List
     * @exception Exception
     */
	@Override
	public List<?> selectDrugInfoListAll(DrugInfoVO vo) throws Exception {
		return sql.selectList(namespace+".selectDrugInfoListAll",vo);
	}

	/**
     * 의약품 기본정보 목록을 객체를 신규 등록한다.
     * @param vo DrugInfoVO
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertDrugInfoOne(DrugInfoVO vo) throws Exception {
		return sql.insert(namespace+".insertDrugInfoOne", vo);
	}
	
	/**
     * 의약품 기본정보 목록 리스트 객체를 신규 등록한다.
     * @param DrugInfoList List
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertDrugInfoList(List<?> DrugInfoList) throws Exception {
		// TODO Auto-generated method stub
		return sql.insert(namespace+".insertDrugInfoList", DrugInfoList);
	}

	/**
     * 의약품 기본정보 목록 특정 객체를 조회한다.
     * @param vo DrugInfoVO
     * @return List
     * @exception Exception
     */
	@Override
	public List<?> selectDrugInfoSearchList(DrugInfoVO vo) throws Exception {
		return sql.selectList(namespace+".selectDrugInfoSearchList",vo);
	}

	/**
     * 의약품 기본정보 목록 특정 객체의 갯수를 조회한다.
     * @param vo DrugInfoVO
     * @return Integer
     * @exception Exception
     */
	@Override
	public int selectDrugInfoSearchListCnt(DrugInfoVO vo) throws Exception {
		return sql.selectOne(namespace+".selectDrugInfoSearchListCnt",vo);
	}

}
