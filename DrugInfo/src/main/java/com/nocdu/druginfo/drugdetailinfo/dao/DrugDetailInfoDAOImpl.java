package com.nocdu.druginfo.drugdetailinfo.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;

@Repository(value="DrugDetailInfoDAO")
public class DrugDetailInfoDAOImpl implements DrugDetailInfoDAO {

	@Inject
	private SqlSession sql;
	
	private static String namespace = "admin";
	
	@Override
	public List<?> selectDrugDetailInfoListAll(DrugDetailInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(namespace+".selectDrugDetailInfoListAll",vo);
	}

	@Override
	public int insertDrugDetailInfoOne(DrugDetailInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.insert(namespace+".insertDrugDetailInfoOne",vo);
	}

	@Override
	public int insertDrugDetailInfoList(List<?> drugDetailInfoList) throws Exception {
		// TODO Auto-generated method stub
		return sql.insert(namespace+".insertDrugDetailInfoList",drugDetailInfoList);
	}

}
