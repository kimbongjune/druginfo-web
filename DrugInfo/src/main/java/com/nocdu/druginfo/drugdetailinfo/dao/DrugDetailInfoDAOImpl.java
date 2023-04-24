package com.nocdu.druginfo.drugdetailinfo.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;

/**
 * 	@author 김봉준
 *	의약품 상세정보 데이터베이스 DAO 구현 클래스
 */
@Repository(value="DrugDetailInfoDAO")
public class DrugDetailInfoDAOImpl implements DrugDetailInfoDAO {

	@Inject
	private SqlSession sql;
	
	//mybatis xml의 namespace
	private static String namespace = "admin";
	
	/**
     * 의약품 상세 정보를 전체 조회한다.
     * @param vo DrugDetailInfoVO
     * @return List
     * @exception Exception
     */
	@Override
	public List<?> selectDrugDetailInfoListAll(DrugDetailInfoVO vo) throws Exception {
		return sql.selectList(namespace+".selectDrugDetailInfoListAll",vo);
	}

	/**
     * 의약품 상세 정보 객체를 신규 등록한다.
     * @param vo DrugDetailInfoVO
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertDrugDetailInfoOne(DrugDetailInfoVO vo) throws Exception {
		return sql.insert(namespace+".insertDrugDetailInfoOne",vo);
	}

	/**
     * 의약품 상세 정보리스트 객체를 신규 등록한다.
     * @param drugDetailInfoList List
     * @return Integer
     * @exception Exception
     */
	@Override
	public int insertDrugDetailInfoList(List<?> drugDetailInfoList) throws Exception {
		return sql.insert(namespace+".insertDrugDetailInfoList",drugDetailInfoList);
	}

}
