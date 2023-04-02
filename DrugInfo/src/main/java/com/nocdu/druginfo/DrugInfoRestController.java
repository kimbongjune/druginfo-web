package com.nocdu.druginfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nocdu.druginfo.drugdetailinfo.service.DrugDetailInfoServiceImpl;
import com.nocdu.druginfo.druginfo.vo.DrugInfoVO;
import com.nocdu.druginfo.pillinfo.service.PillInfoServiceImpl;
import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

@RestController
@RequestMapping("/drugsearch")
public class DrugInfoRestController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(DrugInfoRestController.class);
	
    /** **/
    @Resource(name = "drugInfoServiceImpl")
    private com.nocdu.druginfo.druginfo.service.DrugInfoServiceImpl drugInfoServiceImpl;
    
    /** **/
    @Resource(name = "pillInfoServiceImpl")
    private PillInfoServiceImpl pillInfoServiceImpl;
    
    /** **/
    @Resource(name = "drugDetailInfoServiceImpl")
    private DrugDetailInfoServiceImpl drugDetailInfoServiceImpl;
    
    @ResponseBody
    @RequestMapping(value= { "/textsearch"},method=RequestMethod.GET)
	public ModelAndView getDrugTextSearchResult(HttpServletRequest request, HttpSession session,
			@RequestParam(name="query",value="query", required=false) String query,
			@RequestParam(name="page",value="page",defaultValue="1",required=false) int page,
			@RequestParam(name="shape", value="shape",defaultValue="", required=false) String shape,
			@RequestParam(name="dosageForm", value="dosageForm",defaultValue="", required=false) String dosageForm,
			@RequestParam(name="printFront", value="printFront",defaultValue="", required=false) String printFront,
			@RequestParam(name="printBack", value="printBack",defaultValue="", required=false) String printBack,
			@RequestParam(name="colorClass", value="colorClass",defaultValue="", required=false) String colorClass,
			@RequestParam(name="line", value="line",defaultValue="", required=false) String line)throws Exception {
			ModelAndView model = new ModelAndView("jsonView");
			
			if(page <= 0) {
				page = 1;
			}
			int defaltLimitPage = 15;
			
			DrugInfoVO searchParamVO = new DrugInfoVO();
			PillInfoVO pillInfoVO = new PillInfoVO();
			
			pillInfoVO.setDRUG_SHAPE(shape);
			pillInfoVO.setFORM_CODE_NAME(dosageForm);
			pillInfoVO.setPRINT_FRONT(printFront);
			pillInfoVO.setPRINT_BACK(printBack);
			pillInfoVO.setCOLOR_CLASS1(colorClass);
			pillInfoVO.setLINE_FRONT(line);
			
			searchParamVO.setPillInfoVO(pillInfoVO);
			searchParamVO.setItemName(query);
			
			
			page = (page - 1) * defaltLimitPage;
			searchParamVO.setPage(page);
			//System.out.println("query = "+query);
			System.out.println("page = "+page);
			Map<String, Object> map = null;
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			
			int totalCount = drugInfoServiceImpl.selectDrugInfoSearchListCnt(searchParamVO);
			System.out.println("count = "+totalCount);
			
			Map<String, Object> metaMap = new LinkedHashMap<String, Object>();
			boolean isEnd = totalCount >= page+defaltLimitPage;
			metaMap.put("total_count", totalCount);
			metaMap.put("is_end", isEnd);
			
			List<DrugInfoVO> searchResult = (List<DrugInfoVO>)drugInfoServiceImpl.selectDrugInfoSearchList(searchParamVO);
			
			for(DrugInfoVO vo: searchResult) {
				map = new LinkedHashMap<String, Object>();
				//System.out.println(vo.getEntpName());
				map.put("entp_name", vo.getEntpName());
				//System.out.println(vo.getItemName());
				map.put("item_name", vo.getItemName());
				//System.out.println(vo.getItemSeq());
				map.put("item_seq", vo.getItemSeq());
				//System.out.println(vo.getEfcyQesitm());
				map.put("efcy_qesitm", vo.getEfcyQesitm());
				//System.out.println(vo.getUseMethodQesitm());
				map.put("use_method_qesitm", vo.getUseMethodQesitm());
				//System.out.println(vo.getPillInfoVO().getITEM_IMAGE());
				map.put("item_image", vo.getPillInfoVO().getITEM_IMAGE());
				//System.out.println(vo.getPillInfoVO().getCLASS_NAME());
				map.put("class_name", vo.getPillInfoVO().getCLASS_NAME());
				//System.out.println(vo.getDrugDetailInfoVO().getWARNING_TEXT());
				map.put("warning_text", vo.getDrugDetailInfoVO().getWARNING_TEXT());
				//System.out.println(vo.getDrugDetailInfoVO().getNO_INJECT());
				map.put("no_inject", vo.getDrugDetailInfoVO().getNO_INJECT());
				//System.out.println(vo.getDrugDetailInfoVO().getCAUTION_INJECT());
				map.put("caution_inject", vo.getDrugDetailInfoVO().getCAUTION_INJECT());
				//System.out.println(vo.getDrugDetailInfoVO().getALLERGY_REACTION());
				map.put("allegy_reaction", vo.getDrugDetailInfoVO().getALLERGY_REACTION());
				//System.out.println(vo.getDrugDetailInfoVO().getGENERAL_CAUTION());
				map.put("general_caution", vo.getDrugDetailInfoVO().getGENERAL_CAUTION());
				//System.out.println(vo.getDrugDetailInfoVO().getMULTIE_INJECT_WARNING());
				map.put("multie_inject_warning", vo.getDrugDetailInfoVO().getMULTIE_INJECT_WARNING());
				//System.out.println(vo.getDrugDetailInfoVO().getPREGNANT_WOMEN_INJECT_WARNING());
				map.put("pregnant_women_inject_warning", vo.getDrugDetailInfoVO().getPREGNANT_WOMEN_INJECT_WARNING());
				//System.out.println(vo.getDrugDetailInfoVO().getLACTIATION_WOMEN_INJECT_WARNING());
				map.put("laction_women_inject_warning", vo.getDrugDetailInfoVO().getLACTIATION_WOMEN_INJECT_WARNING());
				//System.out.println(vo.getDrugDetailInfoVO().getCHILD_INJECT_WARNING());
				map.put("child_inject_warning", vo.getDrugDetailInfoVO().getCHILD_INJECT_WARNING());
				//System.out.println(vo.getDrugDetailInfoVO().getOLDMAN_INJECT_WARNING());
				map.put("oldman_inject_warning", vo.getDrugDetailInfoVO().getOLDMAN_INJECT_WARNING());
				//System.out.println(vo.getDrugDetailInfoVO().getPREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING());
				map.put("pregnant_women_with_lactation_woman_warning", vo.getDrugDetailInfoVO().getPREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING());
				//System.out.println(vo.getDrugDetailInfoVO().getOVERDOSE_TREATMENT());
				map.put("overdose_treatment", vo.getDrugDetailInfoVO().getOVERDOSE_TREATMENT());
				//System.out.println(vo.getDrugDetailInfoVO().getDOSE_CAUTION());
				map.put("dose_caution", vo.getDrugDetailInfoVO().getDOSE_CAUTION());
				//System.out.println(vo.getDrugDetailInfoVO().getBEFORE_CONSULT_DOCTOR());
				map.put("before_consult_doctor", vo.getDrugDetailInfoVO().getBEFORE_CONSULT_DOCTOR());
				//System.out.println(vo.getDrugDetailInfoVO().getAFTER_CONSULT_DOCTOR());
				map.put("after_consult_doctor", vo.getDrugDetailInfoVO().getAFTER_CONSULT_DOCTOR());
				//System.out.println(vo.getDrugDetailInfoVO().getINTERACTION_CAUTION());
				map.put("interaction_caution", vo.getDrugDetailInfoVO().getINTERACTION_CAUTION());
				//System.out.println(vo.getDrugDetailInfoVO().getEXTRA_CAUTION());
				map.put("extra_caution", vo.getDrugDetailInfoVO().getEXTRA_CAUTION());
				//System.out.println(vo.getDrugDetailInfoVO().getSTORAGE_METHOD());
				map.put("storage_method", vo.getDrugDetailInfoVO().getSTORAGE_METHOD());
				//System.out.println(vo.getDrugDetailInfoVO().getVALID_TERM());
				map.put("valid_term", vo.getDrugDetailInfoVO().getVALID_TERM());
				//System.out.println(vo.getDrugDetailInfoVO().getEDI_CODE());
				map.put("edi_code", vo.getDrugDetailInfoVO().getEDI_CODE());
				list.add(map);
			}
			//System.out.println(list);
			
			model.addObject("documents", list);
			model.addObject("meta", metaMap);
			return model;
    }
}
