package com.nocdu.druginfo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.tomcat.util.buf.StringUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.xml.sax.InputSource;

import com.nocdu.druginfo.drugdetailinfo.service.DrugDetailInfoService;
import com.nocdu.druginfo.drugdetailinfo.service.DrugDetailInfoServiceImpl;
import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;
import com.nocdu.druginfo.druginfo.service.DrugInfoService;
import com.nocdu.druginfo.druginfo.service.DrugInfoServiceImpl;
import com.nocdu.druginfo.druginfo.vo.DrugInfoVO;
import com.nocdu.druginfo.pillinfo.service.PillInfoService;
import com.nocdu.druginfo.pillinfo.service.PillInfoServiceImpl;
import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

/**
 * 	@author 김봉준
 *	공공데이터에서 의약품 api를 호출하는 클래스
 *	반환받은 데이터를 데이터베이스에 인서트 한다.
 *	TODO 추후에 스케줄러를 이용해 자동으로 갱신하게 변경할 예정, api호출을 Restcontroller로 변경 할 예정
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/** 의약품 기본정보 Service **/
    @Resource(name = "drugInfoServiceImpl")
    private DrugInfoService drugInfoServiceImpl;
    
    /** 의약품 식별정보 Service **/
    @Resource(name = "pillInfoServiceImpl")
    private PillInfoService pillInfoServiceImpl;
    
    /** 의약품 상세정보 Service **/
    @Resource(name = "drugDetailInfoServiceImpl")
    private DrugDetailInfoService drugDetailInfoServiceImpl;
	
//	/**
//	 * Simply selects the home view to render by returning its name.
//	 */
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		return "home";
//	}
	
    /**
     * 개인정보 처리방침 컨트롤러
     * @author 김봉준
     * @param locale
     * @param model
     * @return
     */
	@RequestMapping(value = "/agreements", produces = "application/json; charset=utf8", method = RequestMethod.GET)
	public String agreements(Locale locale, Model model) {
		
		return "agreements";
	}
	
	/**
	 * 의약품 기본정보 API 호출 컨트롤러
	 * 1건 호출 후 전체 데이터 개수를 반환받으며 반환받은 개수를 파라미터로 데이터베이스 삽입 함수를 호출한다
	 * @author 김봉준
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/insert/druginfo", produces = "application/json; charset=utf8", method = RequestMethod.GET)
	@ResponseBody
	public String insertDrugInfoApi(Locale locale, Model model) throws Exception {
		StringBuffer result = new StringBuffer();
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=myYHhhNxJO5zGLT39cjBldHCap4TWme/JU4ubw5WcPfX0CX5CIFLuEA6N0zH115SujHcKBLUbGsxo/Nn8jIVDw=="); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과수*/
        urlBuilder.append("&type=json"); /*결과 json 포맷*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line + "\n");
        }
        rd.close();
        conn.disconnect();
        
        String jsonresult = result.toString().replaceAll("\\<[^>]*>","");
        jsonresult = jsonresult.replaceAll("\\\\n", "");
        
        JsonElement element = JsonParser.parseString(jsonresult);
        
        JsonObject bodyObject = element.getAsJsonObject().get("body").getAsJsonObject();
        
        int searchCount = Integer.parseInt(bodyObject.getAsJsonObject().get("totalCount").toString());
        System.out.println("searchCount = "+searchCount);
        //drugInfoAutoInsert(searchCount);
        return "";
	}
	
	/**
	 * 의약품 식별정보 API 호출 컨트롤러
	 * 1건 호출 후 전체 데이터 개수를 반환받으며 반환받은 개수를 파라미터로 데이터베이스 삽입 함수를 호출한다
	 * @author 김봉준
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/insert/pillinfo", produces = "application/json; charset=utf8", method = RequestMethod.GET)
	@ResponseBody
	public String insertPillInfoApi(Locale locale, Model model) throws Exception {
		StringBuffer result = new StringBuffer();
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/MdcinGrnIdntfcInfoService01/getMdcinGrnIdntfcInfoList01"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=myYHhhNxJO5zGLT39cjBldHCap4TWme/JU4ubw5WcPfX0CX5CIFLuEA6N0zH115SujHcKBLUbGsxo/Nn8jIVDw=="); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과수*/
        urlBuilder.append("&type=json"); /*결과 json 포맷*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line + "\n");
        }
        rd.close();
        conn.disconnect();
        String jsonresult = result.toString().replaceAll("\\<[^>]*>","");
        jsonresult = jsonresult.replaceAll("\\\\n", "");
        
        JsonElement element = JsonParser.parseString(jsonresult);
        
        JsonObject bodyObject = element.getAsJsonObject().get("body").getAsJsonObject();
        
        int searchCount = Integer.parseInt(bodyObject.getAsJsonObject().get("totalCount").toString());
        System.out.println("searchCount = "+searchCount);
        
        //pillInfoAutoInsert(searchCount);
        
        return "";
	}
	
	
	/**
	 * 의약품 식별정보 API 호출 컨트롤러
	 * 1건 호출 후 전체 데이터 개수를 반환받으며 반환받은 개수를 파라미터로 데이터베이스 삽입 함수를 호출한다
	 * @author 김봉준
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/insert/drugdetailinfo", produces = "application/xml; charset=utf8", method = RequestMethod.GET)
	@ResponseBody
	public String insertDrguDetailInfoApi(Locale locale, Model model) throws Exception {
		
		int totalCount = 0;
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService02/getDrugPrdtPrmsnDtlInq01"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=J0frJTCfeYU26mgD2YtyYdY4mIO4NuNyEG8itMIQ55D9fhmbwTzGaBrUi33mCZXO3RDJf4ra0qvBibpW1%2BXSVg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과수*/
        urlBuilder.append("&type=xml"); /*결과 json 포맷*/
        
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      	DocumentBuilder builder = builderFactory.newDocumentBuilder();
      	Document document = builder.parse(urlBuilder.toString());
      	document.getDocumentElement().normalize();
      	
      	NodeList nListCount = document.getElementsByTagName("body");
      	for (int temp = 0; temp < nListCount.getLength(); temp++) {
      		Node nNode = nListCount.item(temp);
      		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
      			Element eElement = (Element) nNode;
      			System.out.println("totalCount : " + eElement.getElementsByTagName("totalCount").item(0).getTextContent());
      			totalCount = Integer.parseInt(eElement.getElementsByTagName("totalCount").item(0).getTextContent().toString());
      		}
      	}
      	System.out.println(totalCount);
      	//drugDetailInfoAutoInsert(totalCount);
            
//            DrugDetailInfoVO vos = new DrugDetailInfoVO();
//            List<DrugDetailInfoVO> resultVo = (List<DrugDetailInfoVO>)drugDetailInfoServiceImpl.selectDrugDetailInfoListAll(vos);
//            
//            for(DrugDetailInfoVO vo : resultVo) {
//            	System.out.println(vo.getId());
//            	System.out.println(vo.getITEM_SEQ());
//            	System.out.println(vo.getITEM_NAME());
//            	System.out.println(vo.getENTP_NAME());
//            	System.out.println(vo.getBAR_CODE());
//            	System.out.println(vo.getUD_DOC_ID());
//            	System.out.println(vo.getSTORAGE_METHOD());
//            	System.out.println(vo.getVALID_TERM());
//            	System.out.println(vo.getEDI_CODE());
//            	System.out.println(vo.getWARNING_TEXT());
//            	System.out.println(vo.getNO_INJECT());
//            	System.out.println(vo.getCAUTION_INJECT());
//            	System.out.println(vo.getALLERGY_REACTION());
//            	System.out.println(vo.getGENERAL_CAUTION());
//            	System.out.println(vo.getMULTIE_INJECT_WARNING());
//            	System.out.println(vo.getPREGNANT_WOMEN_INJECT_WARNING());
//            	System.out.println(vo.getLACTIATION_WOMEN_INJECT_WARNING());
//            	System.out.println(vo.getCHILD_INJECT_WARNING());
//            	System.out.println(vo.getOLDMAN_INJECT_WARNING());
//            	System.out.println(vo.getPREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING());
//            	System.out.println(vo.getOVERDOSE_TREATMENT());
//            	System.out.println(vo.getDOSE_CAUTION());
//            	System.out.println(vo.getBEFORE_CONSULT_DOCTOR());
//            	System.out.println(vo.getAFTER_CONSULT_DOCTOR());
//            	System.out.println(vo.getINTERACTION_CAUTION());
//            	System.out.println(vo.getEXTRA_CAUTION());
//            }
		return "";
	}
	
	/**
	 * 의약품 상세정보 데이터베이스 삽입 함수
	 * 파라미터로 받은 개수 만큼 반복문을돌며 한번에 100건식 데이터베이스에 벌크인서트를 한다.
	 * 해당 데이터는 XML로 파싱한다. xml 태그 자체가 데이터로 들어가 있어 json 형태로 파싱하기 곤란한 문제가 있었음
	 * @author 김봉준
	 * @param count
	 */
	public void drugDetailInfoAutoInsert(int count){
		System.out.println("==============================인서트 시작==============================");
		try {
		for(int ia = 1; ia <= count/100+1; ia ++) {
			List<DrugDetailInfoVO> drugInfoList = new ArrayList<>();
			System.out.println("index :"+ia);
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService02/getDrugPrdtPrmsnDtlInq01"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=J0frJTCfeYU26mgD2YtyYdY4mIO4NuNyEG8itMIQ55D9fhmbwTzGaBrUi33mCZXO3RDJf4ra0qvBibpW1%2BXSVg%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(ia), "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과수*/
	        urlBuilder.append("&type=xml"); /*결과 json 포맷*/
	        
	        
	        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	      	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	      	Document document = builder.parse(urlBuilder.toString());
	      	document.getDocumentElement().normalize();
	        
	      	NodeList nList = document.getElementsByTagName("item");
	      	for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
//	            System.out.println("\nCurrent Element :" + nNode.getNodeName());
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	DrugDetailInfoVO searchVo = new DrugDetailInfoVO();
	                Element eElement = (Element) nNode;
	                String ITEM_SEQ = Jsoup.parse(eElement.getElementsByTagName("ITEM_SEQ").item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text();
	                String ITEM_NAME = Jsoup.parse(eElement.getElementsByTagName("ITEM_NAME").item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text();
	                String ENTP_NAME = Jsoup.parse(eElement.getElementsByTagName("ENTP_NAME").item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text();
	                String BAR_CODE = Jsoup.parse(eElement.getElementsByTagName("BAR_CODE").item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text();
	                String UD_DOC_ID = Jsoup.parse(eElement.getElementsByTagName("UD_DOC_DATA").item(0).getTextContent().replace("\n", "").replace("\r", "").trim()).text();
	                String STORAGE_METHOD = Jsoup.parse(eElement.getElementsByTagName("STORAGE_METHOD").item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text();
	                String VALID_TERM = Jsoup.parse(eElement.getElementsByTagName("VALID_TERM").item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text();
	                String EDI_CODE = Jsoup.parse(eElement.getElementsByTagName("EDI_CODE").item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text();

	                
	                Node companyNode = eElement.getElementsByTagName("NB_DOC_DATA").item(0);
	                if(companyNode.getChildNodes().item(0) == null) {
	                	continue;
	                }
	                NodeList companyChildNodes = companyNode.getChildNodes().item(0).getChildNodes();
//	                System.out.println("length" + companyChildNodes.getLength());
	                for(int i = 0; i < companyChildNodes.getLength(); i ++) {
	                	Node node = companyChildNodes.item(i);
	                	//System.out.println("@@@@@@@@################@"+node.getNodeName().replace(" ", "").replace("\n", "").replace("\r", "").trim());
	                    if (node.getNodeType() == Node.ELEMENT_NODE && Objects.equals("SECTION", node.getNodeName())) {
//	                    	System.out.println("@@@@@@!!!!!"+((Element)node).getAttribute("title"));
//	                        System.out.println("@@@@@@@@@"+node.getTextContent());
	                        NodeList NodeList2 = node.getChildNodes();
	                        for(int j = 0; j < NodeList2.getLength(); j ++) {
	                        	Node node2 = NodeList2.item(j);
	                        	if (node2.getNodeType() == Node.ELEMENT_NODE && Objects.equals("ARTICLE", node2.getNodeName())) {
	                        		String title = ((Element)node2).getAttribute("title");
	                        		//System.out.println("@@@@@@@@################@"+node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim());
	                        		//System.out.println("@@@@@@!!!!!"+((Element)node2).getAttribute("title"));
		                            if(title.contains("경고")) {
		                            	//System.out.println("WARNING_TEXT");
		                            	searchVo.setWARNING_TEXT(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("투여하지 말")) {
		                            	//System.out.println("NO_INJECT");
		                            	searchVo.setNO_INJECT(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("신중히")) {
		                            	//System.out.println("CAUTION_INJECT");
		                            	searchVo.setCAUTION_INJECT(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("이상반응")) {
		                            	//System.out.println("ALLERGY_REACTION");
		                            	searchVo.setALLERGY_REACTION(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" +Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            }else if(title.contains("일반적 주의")) {
		                            	//System.out.println("GENERAL_CAUTION");
		                            	searchVo.setGENERAL_CAUTION(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" +Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            }else if(title.contains("임부, 수유부")) {
		                            	//System.out.println("MULTIE_INJECT_WARNING");
		                            	searchVo.setMULTIE_INJECT_WARNING(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("임부에 대한")) {
		                            	//System.out.println("PREGNANT_WOMEN_INJECT_WARNING");
		                            	searchVo.setPREGNANT_WOMEN_INJECT_WARNING(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("수유부에 대한")) {
		                            	//System.out.println("LACTIATION_WOMEN_INJECT_WARNING");
		                            	searchVo.setLACTIATION_WOMEN_INJECT_WARNING(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("소아에 대한")) {
		                            	//System.out.println("CHILD_INJECT_WARNING");
		                            	searchVo.setCHILD_INJECT_WARNING(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("고령자에 대한")) {
		                            	//System.out.println("OLDMAN_INJECT_WARNING");
		                            	searchVo.setOLDMAN_INJECT_WARNING(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("임부 및 수유부")) {
		                            	//System.out.println("PREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING");
		                            	searchVo.setPREGNANT_WOMEN_WITH_LACTIATION_WOMEN_WARNING(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("과량투여")) {
		                            	//System.out.println("OVERDOSE_TREATMENT");
		                            	searchVo.setOVERDOSE_TREATMENT(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("적용상의")) {
		                            	//System.out.println("DOSE_CAUTION");
		                            	searchVo.setDOSE_CAUTION(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("이 약을 복용하기 전에")) {
		                            	//System.out.println("BEFORE_CONSULT_DOCTOR");
		                            	searchVo.setBEFORE_CONSULT_DOCTOR(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("이 약의 복용을 즉각 중지하고")) {
		                            	//System.out.println("AFTER_CONSULT_DOCTOR");
		                            	searchVo.setAFTER_CONSULT_DOCTOR(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("상호작용")) {
		                            	//System.out.println("INTERACTION_CAUTION");
		                            	searchVo.setINTERACTION_CAUTION(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }else if(title.contains("보관 및") || title.contains("저장상")){
		                            	continue;
		                            }else {
		                            	//System.out.println("EXTRA_CAUTION");
		                            	searchVo.setEXTRA_CAUTION(Jsoup.parse(node2.getTextContent().replace("\n", "").replace("\r", "").replaceAll(" +", " ").trim()).text());
		                            	//System.out.println("data" + node2.getTextContent());
		                            }
	                        	}
	                        }
	                    }
	                }
	                searchVo.setITEM_SEQ(ITEM_SEQ);
	                searchVo.setITEM_NAME(ITEM_NAME);
	                searchVo.setENTP_NAME(ENTP_NAME);
	                searchVo.setBAR_CODE(BAR_CODE);
	                searchVo.setUD_DOC_ID(UD_DOC_ID);
	                searchVo.setSTORAGE_METHOD(STORAGE_METHOD);
	                searchVo.setVALID_TERM(VALID_TERM);
	                searchVo.setEDI_CODE(EDI_CODE);
	                drugInfoList.add(searchVo);
	                //drugDetailInfoServiceImpl.insertDrugDetailInfoOne(searchVo);
	                //Thread.sleep(200);
	            }
	      	}
	      	drugDetailInfoServiceImpl.insertDrugDetailInfoList(drugInfoList);
		}
		System.out.println("==============================인서트 종료==============================");
		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 의약품 기본정보 데이터베이스 삽입 함수
	 * 파라미터로 받은 개수 만큼 반복문을돌며 한번에 100건식 데이터베이스에 벌크인서트를 한다.
	 * @author 김봉준
	 * @param count
	 * @throws Exception
	 */
	public void drugInfoAutoInsert(int count) throws Exception{
		for(int i = 1; i <= count/100+1; i ++) {
			StringBuffer result = new StringBuffer();
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=myYHhhNxJO5zGLT39cjBldHCap4TWme/JU4ubw5WcPfX0CX5CIFLuEA6N0zH115SujHcKBLUbGsxo/Nn8jIVDw=="); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(i), "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과수*/
	        urlBuilder.append("&type=json"); /*결과 json 포맷*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        BufferedReader rd;
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        String line;
	        while ((line = rd.readLine()) != null) {
	            result.append(line + "\n");
	        }
	        //System.out.println("result : "+ result.toString());
	        rd.close();
	        conn.disconnect();
	        String jsonresult = result.toString().replaceAll("\\<[^>]*>","");
	        jsonresult = jsonresult.replaceAll("\\\\n", "");
	        
	        JsonElement element = JsonParser.parseString(jsonresult);
	        
	        JsonObject bodyObject = element.getAsJsonObject().get("body").getAsJsonObject();
	        
	        int searchCount = Integer.parseInt(bodyObject.getAsJsonObject().get("totalCount").toString());
	        System.out.println("searchCount = "+searchCount);
	        JsonArray itemArray = (JsonArray) bodyObject.getAsJsonArray("items").getAsJsonArray();
	        Gson gson = new Gson();
	        
	        List<DrugInfoVO> drugInfoList = gson.fromJson(itemArray.toString(), new TypeToken<List<DrugInfoVO>>(){}.getType());
	        if(drugInfoList.size() > 0) {
	        	//drugInfoServiceImpl.insertDrugInfoList(drugInfoList);
	        }
	        System.out.println("==============================");
		}
		
	}
	
	/**
	 * 의약품 식별정보 데이터베이스 삽입 함수
	 * 파라미터로 받은 개수 만큼 반복문을돌며 한번에 100건식 데이터베이스에 벌크인서트를 한다.
	 * @author 김봉준
	 * @param count
	 * @throws Exception
	 */
	public void pillInfoAutoInsert(int count) throws Exception{
		System.out.println("===============인서트 시작===============");
		for(int i = 1; i <= count/100+1; i ++) {
			StringBuffer result = new StringBuffer();
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/MdcinGrnIdntfcInfoService01/getMdcinGrnIdntfcInfoList01"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=myYHhhNxJO5zGLT39cjBldHCap4TWme/JU4ubw5WcPfX0CX5CIFLuEA6N0zH115SujHcKBLUbGsxo/Nn8jIVDw=="); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(i), "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과수*/
	        urlBuilder.append("&type=json"); /*결과 json 포맷*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        BufferedReader rd;
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        String line;
	        while ((line = rd.readLine()) != null) {
	            result.append(line + "\n");
	        }
	        //System.out.println("result : "+ result.toString());
	        rd.close();
	        conn.disconnect();
	        String jsonresult = result.toString().replaceAll("\\<[^>]*>","");
	        jsonresult = jsonresult.replaceAll("\\\\n", "");
	        
	        JsonElement element = JsonParser.parseString(jsonresult);
	        
	        JsonObject bodyObject = element.getAsJsonObject().get("body").getAsJsonObject();
	        
	        int searchCount = Integer.parseInt(bodyObject.getAsJsonObject().get("totalCount").toString());
	        //System.out.println("searchCount = "+searchCount);
	        JsonArray itemArray = (JsonArray) bodyObject.getAsJsonArray("items").getAsJsonArray();
	        Gson gson = new Gson();
	        
	        List<PillInfoVO> pillInfoList = gson.fromJson(itemArray.toString(), new TypeToken<List<PillInfoVO>>(){}.getType());
	        if(pillInfoList.size() > 0) {
	        	pillInfoServiceImpl.insertPillInfoList(pillInfoList);
	        }
		}
		System.out.println("===============인서트 종료===============");
	}
}
