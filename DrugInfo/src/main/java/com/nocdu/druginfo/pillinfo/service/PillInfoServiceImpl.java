package com.nocdu.druginfo.pillinfo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nocdu.druginfo.pillinfo.dao.PillInfoDAO;
import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

@Service("pillInfoServiceImpl")
public class PillInfoServiceImpl implements PillInfoService {

    /** **/
    @Resource(name = "PillInfoDAO")
    private PillInfoDAO pillInfoDAO;

	@Override
	public List<?> selectPillInfoListAll(PillInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return pillInfoDAO.selectPillInfoListAll(vo);
	}

	@Override
	public int insertPillInfoOne(PillInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return pillInfoDAO.insertPillInfoOne(vo);
	}

	@Override
	public int insertPillInfoList(List<?> pillInfoList) throws Exception {
		// TODO Auto-generated method stub
		return pillInfoDAO.insertPillInfoList(pillInfoList);
	}
	

}
