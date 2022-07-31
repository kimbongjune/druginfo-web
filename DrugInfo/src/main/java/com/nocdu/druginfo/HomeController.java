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
import com.nocdu.druginfo.service.DrugInfoServiceImpl;
import com.nocdu.druginfo.vo.DrugInfoVO;

import org.xml.sax.InputSource;

import com.nocdu.druginfo.drugdetailinfo.service.DrugDetailInfoServiceImpl;
import com.nocdu.druginfo.drugdetailinfo.vo.DrugDetailInfoVO;
import com.nocdu.druginfo.pillinfo.service.PillInfoServiceImpl;
import com.nocdu.druginfo.pillinfo.vo.PillInfoVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
    /** **/
    @Resource(name = "drugInfoServiceImpl")
    private DrugInfoServiceImpl drugInfoServiceImpl;
    
    /** **/
    @Resource(name = "pillInfoServiceImpl")
    private PillInfoServiceImpl pillInfoServiceImpl;
    
    /** **/
    @Resource(name = "drugDetailInfoServiceImpl")
    private DrugDetailInfoServiceImpl drugDetailInfoServiceImpl;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
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
        JsonArray itemArray = (JsonArray) bodyObject.getAsJsonArray("items").getAsJsonArray();
        Gson gson = new Gson();
        //autoInsert(searchCount);
        return "";
	}
	
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
        JsonArray itemArray = (JsonArray) bodyObject.getAsJsonArray("items").getAsJsonArray();
        Gson gson = new Gson();
        
        //pillInfoAutoInsert(searchCount);
        
        return "";
	}
	
	
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
	public void drugDetailInfoAutoInsert(int count) throws Exception{
		System.out.println("==============================인서트 시작==============================");
		for(int ia = 238; ia <= count/100+1; ia ++) {
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
//	                System.out.println("ITEM_SEQ : " + eElement.getElementsByTagName("ITEM_SEQ").item(0).getTextContent());
//	                System.out.println("ITEM_NAME : " + eElement.getElementsByTagName("ITEM_NAME").item(0).getTextContent());
//	                System.out.println("ENTP_NAME : " + eElement.getElementsByTagName("ENTP_NAME").item(0).getTextContent());
//	                System.out.println("ITEM_PERMIT_DATE : " + eElement.getElementsByTagName("ITEM_PERMIT_DATE").item(0).getTextContent());
//	                System.out.println("CNSGN_MANUF : " + eElement.getElementsByTagName("CNSGN_MANUF").item(0).getTextContent());
//	                System.out.println("ETC_OTC_CODE : " + eElement.getElementsByTagName("ETC_OTC_CODE").item(0).getTextContent());
//	                System.out.println("CHART : " + eElement.getElementsByTagName("CHART").item(0).getTextContent());
//	                System.out.println("BAR_CODE : " + eElement.getElementsByTagName("BAR_CODE").item(0).getTextContent());
//	                System.out.println("MATERIAL_NAME : " + eElement.getElementsByTagName("MATERIAL_NAME").item(0).getTextContent());
//	                System.out.println("EE_DOC_ID : " + eElement.getElementsByTagName("EE_DOC_ID").item(0).getTextContent());
//	                System.out.println("UD_DOC_ID : " + eElement.getElementsByTagName("UD_DOC_ID").item(0).getTextContent());
//	                System.out.println("NB_DOC_ID : " + eElement.getElementsByTagName("NB_DOC_ID").item(0).getTextContent());
//	                System.out.println("INSERT_FILE : " + eElement.getElementsByTagName("INSERT_FILE").item(0).getTextContent());
//	                System.out.println("STORAGE_METHOD : " + eElement.getElementsByTagName("STORAGE_METHOD").item(0).getTextContent());
//	                System.out.println("VALID_TERM : " + eElement.getElementsByTagName("VALID_TERM").item(0).getTextContent());
//	                System.out.println("REEXAM_TARGET : " + eElement.getElementsByTagName("REEXAM_TARGET").item(0).getTextContent());
//	                System.out.println("REEXAM_DATE : " + eElement.getElementsByTagName("REEXAM_DATE").item(0).getTextContent());
//	                System.out.println("PACK_UNIT : " + eElement.getElementsByTagName("PACK_UNIT").item(0).getTextContent());
//	                System.out.println("EDI_CODE : " + eElement.getElementsByTagName("EDI_CODE").item(0).getTextContent());
//	                System.out.println("DOC_TEXT : " + eElement.getElementsByTagName("DOC_TEXT").item(0).getTextContent());
//	                System.out.println("PERMIT_KIND_NAME : " + eElement.getElementsByTagName("PERMIT_KIND_NAME").item(0).getTextContent());
//	                System.out.println("ENTP_NO : " + eElement.getElementsByTagName("ENTP_NO").item(0).getTextContent());
//	                System.out.println("MAKE_MATERIAL_FLAG : " + eElement.getElementsByTagName("MAKE_MATERIAL_FLAG").item(0).getTextContent());
//	                System.out.println("NEWDRUG_CLASS_NAME : " + eElement.getElementsByTagName("NEWDRUG_CLASS_NAME").item(0).getTextContent());
//	                System.out.println("INDUTY_TYPE : " + eElement.getElementsByTagName("INDUTY_TYPE").item(0).getTextContent());
//	                System.out.println("CANCEL_DATE : " + eElement.getElementsByTagName("CANCEL_DATE").item(0).getTextContent());
//	                System.out.println("CANCEL_NAME : " + eElement.getElementsByTagName("CANCEL_NAME").item(0).getTextContent());
//	                System.out.println("CHANGE_DATE : " + eElement.getElementsByTagName("CHANGE_DATE").item(0).getTextContent());
//	                System.out.println("NARCOTIC_KIND_CODE : " + eElement.getElementsByTagName("NARCOTIC_KIND_CODE").item(0).getTextContent());
//	                System.out.println("GBN_NAME : " + eElement.getElementsByTagName("GBN_NAME").item(0).getTextContent());
//	                System.out.println("TOTAL_CONTENT : " + eElement.getElementsByTagName("TOTAL_CONTENT").item(0).getTextContent());
//	                System.out.println("============================================================================================");
//	                System.out.println("EE_DOC_DATA : " + eElement.getElementsByTagName("EE_DOC_DATA").item(0).getTextContent());
//	                System.out.println("UD_DOC_DATA : " + eElement.getElementsByTagName("UD_DOC_DATA").item(0).getTextContent());
//	                System.out.println("NB_DOC_DATA : " + eElement.getElementsByTagName("NB_DOC_DATA").item(0).getTextContent());
//	                System.out.println("============================================================================================");
//	                System.out.println("PN_DOC_DATA : " + eElement.getElementsByTagName("PN_DOC_DATA").item(0).getTextContent());
//	                System.out.println("MAIN_ITEM_INGR : " + eElement.getElementsByTagName("MAIN_ITEM_INGR").item(0).getTextContent());
//	                System.out.println("INGR_NAME : " + eElement.getElementsByTagName("INGR_NAME").item(0).getTextContent());
//	                System.out.println("ATC_CODE : " + eElement.getElementsByTagName("ATC_CODE").item(0).getTextContent());
//	                
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
	                drugDetailInfoServiceImpl.insertDrugDetailInfoOne(searchVo);
	                Thread.sleep(200);
	            }
	      	}
		}
		System.out.println("==============================인서트 종료==============================");
	}
	
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
	        System.out.println("result : "+ result.toString());
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
	            for(DrugInfoVO vo : drugInfoList) {
	            	DrugInfoVO insertVO = new DrugInfoVO();
	            	System.out.println("=============================================================================");
	            	//System.out.println("제약사 = "+vo.getEntpName());
	            	insertVO.setEntpName(vo.getEntpName());
	            	//System.out.println("약품명 = "+vo.getItemName());
	            	insertVO.setItemName(vo.getItemName());
	            	//System.out.println("품목기준코드 = "+vo.getItemSeq());
	            	insertVO.setItemSeq(vo.getItemSeq());
	            	//System.out.println("효능 = "+vo.getEfcyQesitm());
	            	insertVO.setEfcyQesitm(vo.getEfcyQesitm());
	            	//System.out.println("사용법 = "+vo.getUseMethodQesitm());
	            	insertVO.setUseMethodQesitm(vo.getUseMethodQesitm());
	            	//System.out.println("복용 전 주의사항 = "+vo.getAtpnWarnQesitm());
	            	insertVO.setAtpnWarnQesitm(vo.getAtpnWarnQesitm());
	            	//System.out.println("복용 시 주의사항 = "+vo.getAtpnQesitm());
	            	insertVO.setAtpnQesitm(vo.getAtpnQesitm());
	            	//System.out.println("복용 간 주의 약품 및 식품 = "+vo.getIntrcQesitm());
	            	insertVO.setIntrcQesitm(vo.getIntrcQesitm());
	            	//System.out.println("복용 시 부작용 = "+vo.getSeQesitm());
	            	insertVO.setSeQesitm(vo.getSeQesitm());
	            	//System.out.println("보관 방법 = "+vo.getDepositMethodQesitm());
	            	insertVO.setDepositMethodQesitm(vo.getDepositMethodQesitm());
	            	//System.out.println("공개 일자 = "+vo.getOpenDe());
	            	insertVO.setOpenDe(vo.getOpenDe());
	            	//System.out.println("수정 일자 = "+vo.getUpdateDe());
	            	insertVO.setUpdateDe(vo.getUpdateDe());
	            	//System.out.println("이미지 url = "+vo.getItemImage());
	            	insertVO.setItemImage(vo.getItemImage());
	            	drugInfoServiceImpl.insertDrugInfoOne(insertVO);
	            	Thread.sleep(2000);
	            }
	        }
	        System.out.println("==============================");
		}
		
	}
	
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
	            for(PillInfoVO vo : pillInfoList) {
	            	PillInfoVO insertVO = new PillInfoVO();
	            	//System.out.println("품목 일련번호 ="+vo.getITEM_SEQ());
	            	insertVO.setITEM_SEQ(vo.getITEM_SEQ());
	            	//System.out.println("품목 명 ="+vo.getITEM_NAME());
	            	insertVO.setITEM_NAME(vo.getITEM_NAME());
	            	//System.out.println("업체 일련번호 ="+vo.getENTP_SEQ());
	            	insertVO.setENTP_SEQ(vo.getENTP_SEQ());
	            	//System.out.println("업체 명 ="+vo.getENTP_NAME());
	            	insertVO.setENTP_NAME(vo.getENTP_NAME());
	            	//System.out.println("성상 ="+vo.getCHART());
	            	insertVO.setCHART(vo.getCHART());
	            	//System.out.println("큰제품 이미지 ="+vo.getITEM_IMAGE());
	            	insertVO.setITEM_IMAGE(vo.getITEM_IMAGE());
	            	//System.out.println("표시(앞) ="+vo.getPRINT_FRONT());
	            	insertVO.setPRINT_FRONT(vo.getPRINT_FRONT());
	            	//System.out.println("표시(뒤) ="+vo.getPRINT_BACK());
	            	insertVO.setPRINT_BACK(vo.getPRINT_BACK());
	            	//System.out.println("의약품모양 ="+vo.getDRUG_SHAPE());
	            	insertVO.setDRUG_SHAPE(vo.getDRUG_SHAPE());
	            	//System.out.println("색깔(앞) ="+vo.getCOLOR_CLASS1());
	            	insertVO.setCOLOR_CLASS1(vo.getCOLOR_CLASS1());
	            	//System.out.println("색깔(뒤) ="+vo.getCOLOR_CLASS2());
	            	insertVO.setCOLOR_CLASS2(vo.getCOLOR_CLASS2());
	            	//System.out.println("분할선(앞) ="+vo.getLINE_FRONT());
	            	insertVO.setLINE_FRONT(vo.getLINE_FRONT());
	            	//System.out.println("분할선(뒤) ="+vo.getLINE_BACK());
	            	insertVO.setLINE_BACK(vo.getLINE_BACK());
	            	//System.out.println("크기(장축) ="+vo.getLENG_LONG());
	            	insertVO.setLENG_LONG(vo.getLENG_LONG());
	            	//System.out.println("크기(단축) ="+vo.getLENG_SHORT());
	            	insertVO.setLENG_SHORT(vo.getLENG_SHORT());
	            	//System.out.println("크기(두께) ="+vo.getTHICK());
	            	insertVO.setTHICK(vo.getTHICK());
	            	//System.out.println("약학정보원 이미지 생성일 ="+vo.getIMG_REGIST_TS());
	            	insertVO.setIMG_REGIST_TS(vo.getIMG_REGIST_TS());
	            	//System.out.println("분류번호 ="+vo.getCLASS_NO());
	            	insertVO.setCLASS_NO(vo.getCLASS_NO());
	            	//System.out.println("분류명 ="+vo.getCLASS_NAME());
	            	insertVO.setCLASS_NAME(vo.getCLASS_NAME());
	            	//System.out.println("전문/일반 ="+vo.getETC_OTC_NAME());
	            	insertVO.setETC_OTC_NAME(vo.getETC_OTC_NAME());
	            	//System.out.println("품목허가일자 ="+vo.getITEM_PERMIT_DATE());
	            	insertVO.setITEM_PERMIT_DATE(vo.getITEM_PERMIT_DATE());
	            	//System.out.println("제형코드이름 ="+vo.getFORM_CODE_NAME());
	            	insertVO.setFORM_CODE_NAME(vo.getFORM_CODE_NAME());
	            	//System.out.println("마크내용(앞) ="+vo.getMARK_CODE_FRONT_ANAL());
	            	insertVO.setMARK_CODE_FRONT_ANAL(vo.getMARK_CODE_FRONT_ANAL());
	            	//System.out.println("마크내용(뒤) ="+vo.getMARK_CODE_BACK_ANAL());
	            	insertVO.setMARK_CODE_BACK_ANAL(vo.getMARK_CODE_BACK_ANAL());
	            	//System.out.println("마크이미지(앞) ="+vo.getMARK_CODE_FRONT_IMG());
	            	insertVO.setMARK_CODE_FRONT_IMG(vo.getMARK_CODE_FRONT_IMG());
	            	//System.out.println("마크이미지(뒤) ="+vo.getMARK_CODE_BACK_IMG());
	            	insertVO.setMARK_CODE_BACK_IMG(vo.getMARK_CODE_BACK_IMG());
	            	//System.out.println("영문명 ="+vo.getITEM_ENG_NAME());
	            	insertVO.setITEM_ENG_NAME(vo.getITEM_ENG_NAME());
	            	//System.out.println("변경일자 ="+vo.getCHANGE_DATE());
	            	insertVO.setCHANGE_DATE(vo.getCHANGE_DATE());
	            	//System.out.println("마크코드(앞) ="+vo.getMARK_CODE_FRONT());
	            	insertVO.setMARK_CODE_FRONT(vo.getMARK_CODE_FRONT());
	            	//System.out.println("마크코드(뒤) ="+vo.getMARK_CODE_BACK());
	            	insertVO.setMARK_CODE_BACK(vo.getMARK_CODE_BACK());
	            	//System.out.println("보험코드 ="+vo.getEDI_CODE());
	            	insertVO.setEDI_CODE(vo.getEDI_CODE());
	            	
	            	pillInfoServiceImpl.insertPillInfoOne(insertVO);
	            	Thread.sleep(100);
	            }
	        }
		}
		System.out.println("===============인서트 종료===============");
//		PillInfoVO vos = new PillInfoVO();
//		List<PillInfoVO> resultVO = (List<PillInfoVO>)pillInfoServiceImpl.selectPillInfoListAll(vos);
//		for(PillInfoVO vo : resultVO) {
//        	System.out.println("품목 일련번호 ="+vo.getITEM_SEQ());
//        	System.out.println("품목 명 ="+vo.getITEM_NAME());
//        	System.out.println("업체 일련번호 ="+vo.getENTP_SEQ());
//        	System.out.println("업체 명 ="+vo.getENTP_NAME());
//        	System.out.println("성상 ="+vo.getCHART());
//        	System.out.println("큰제품 이미지 ="+vo.getITEM_IMAGE());
//        	System.out.println("표시(앞) ="+vo.getPRINT_FRONT());
//        	System.out.println("표시(뒤) ="+vo.getPRINT_BACK());
//        	System.out.println("의약품모양 ="+vo.getDRUG_SHAPE());
//        	System.out.println("색깔(앞) ="+vo.getCOLOR_CLASS1());
//        	System.out.println("색깔(뒤) ="+vo.getCOLOR_CLASS2());
//        	System.out.println("분할선(앞) ="+vo.getLINE_FRONT());
//        	System.out.println("분할선(뒤) ="+vo.getLINE_BACK());
//        	System.out.println("크기(장축) ="+vo.getLENG_LONG());
//        	System.out.println("크기(단축) ="+vo.getLENG_SHORT());
//        	System.out.println("크기(두께) ="+vo.getTHICK());
//        	System.out.println("약학정보원 이미지 생성일 ="+vo.getIMG_REGIST_TS());
//        	System.out.println("분류번호 ="+vo.getCLASS_NO());
//        	System.out.println("분류명 ="+vo.getCLASS_NAME());
//        	System.out.println("전문/일반 ="+vo.getETC_OTC_NAME());
//        	System.out.println("품목허가일자 ="+vo.getITEM_PERMIT_DATE());
//        	System.out.println("제형코드이름 ="+vo.getFORM_CODE_NAME());
//        	System.out.println("마크내용(앞) ="+vo.getMARK_CODE_FRONT_ANAL());
//        	System.out.println("마크내용(뒤) ="+vo.getMARK_CODE_BACK_ANAL());
//        	System.out.println("마크이미지(앞) ="+vo.getMARK_CODE_FRONT_IMG());
//        	System.out.println("마크이미지(뒤) ="+vo.getMARK_CODE_BACK_IMG());
//        	System.out.println("영문명 ="+vo.getITEM_ENG_NAME());
//        	System.out.println("변경일자 ="+vo.getCHANGE_DATE());
//        	System.out.println("마크코드(앞) ="+vo.getMARK_CODE_FRONT());
//        	System.out.println("마크코드(뒤) ="+vo.getMARK_CODE_BACK());
//        	System.out.println("보험코드 ="+vo.getEDI_CODE());
//		}
	}
}
