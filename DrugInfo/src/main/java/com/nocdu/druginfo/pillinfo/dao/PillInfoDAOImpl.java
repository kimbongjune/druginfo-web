package com.nocdu.druginfo.pillinfo.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

@Repository(value="PillInfoDAO")
public class PillInfoDAOImpl implements PillInfoDAO {
	
	@Inject
	private SqlSession sql;
	
	private static String namespace = "admin";

	@Override
	public List<?> selectPillInfoListAll(PillInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(namespace+".selectPillInfoListAll");
	}

	@Override
	public int insertPillInfoOne(PillInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.insert(namespace+".insertPillInfoOne", vo);
	}
}
