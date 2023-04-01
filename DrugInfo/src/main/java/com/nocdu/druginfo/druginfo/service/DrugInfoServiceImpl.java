package com.nocdu.druginfo.druginfo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nocdu.druginfo.druginfo.dao.DrugInfoDAO;
import com.nocdu.druginfo.druginfo.vo.DrugInfoVO;


@Service("drugInfoServiceImpl")
public class DrugInfoServiceImpl implements DrugInfoService {

    /** **/
    @Resource(name = "DrugInfoDAO")
    private DrugInfoDAO drugInfoDAO;
	
	@Override
	public List<?> selectDrugInfoListAll(DrugInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return drugInfoDAO.selectDrugInfoListAll(vo);
	}

	@Override
	public int insertDrugInfoOne(DrugInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return drugInfoDAO.insertDrugInfoOne(vo);
	}
	
	@Override
	public int insertDrugInfoList(List<?> DrugInfoList) throws Exception {
		// TODO Auto-generated method stub
		return drugInfoDAO.insertDrugInfoList(DrugInfoList);
	}

	@Override
	public List<?> selectDrugInfoSearchList(DrugInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return drugInfoDAO.selectDrugInfoSearchList(vo);
	}

	@Override
	public int selectDrugInfoSearchListCnt(DrugInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return drugInfoDAO.selectDrugInfoSearchListCnt(vo);
	}

}
