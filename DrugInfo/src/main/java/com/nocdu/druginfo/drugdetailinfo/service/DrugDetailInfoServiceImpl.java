package com.nocdu.druginfo.drugdetailinfo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nocdu.druginfo.drugdetailinfo.dao.DrugDetailInfoDAO;
import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;

@Service("drugDetailInfoServiceImpl")
public class DrugDetailInfoServiceImpl implements DrugDetailInfoService {

	
    /** **/
    @Resource(name = "DrugDetailInfoDAO")
    private DrugDetailInfoDAO DrugDetailInfoDAO;
    
	@Override
	public List<?> selectDrugDetailInfoListAll(DrugDetailInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return DrugDetailInfoDAO.selectDrugDetailInfoListAll(vo);
	}

	@Override
	public int insertDrugDetailInfoOne(DrugDetailInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return DrugDetailInfoDAO.insertDrugDetailInfoOne(vo);
	}

	@Override
	public int insertDrugDetailInfoList(List<?> drugDetailInfoList) throws Exception {
		// TODO Auto-generated method stub
		return DrugDetailInfoDAO.insertDrugDetailInfoList(drugDetailInfoList);
	}


}
