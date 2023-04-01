package com.nocdu.druginfo.druginfo.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.nocdu.druginfo.druginfo.vo.DrugInfoVO;


@Repository(value="DrugInfoDAO")
public class DrugInfoDAOImpl implements DrugInfoDAO {
	
	@Inject
	private SqlSession sql;
	
	private static String namespace = "admin";

	@Override
	public List<?> selectDrugInfoListAll(DrugInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(namespace+".selectDrugInfoListAll",vo);
	}

	@Override
	public int insertDrugInfoOne(DrugInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.insert(namespace+".insertDrugInfoOne", vo);
	}
	
	@Override
	public int insertDrugInfoList(List<?> DrugInfoList) throws Exception {
		// TODO Auto-generated method stub
		return sql.insert(namespace+".insertDrugInfoList", DrugInfoList);
	}

	@Override
	public List<?> selectDrugInfoSearchList(DrugInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(namespace+".selectDrugInfoSearchList",vo);
	}

	@Override
	public int selectDrugInfoSearchListCnt(DrugInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace+".selectDrugInfoSearchListCnt",vo);
	}

}
