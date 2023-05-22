package com.nocdu.druginfo.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.nocdu.druginfo.drugdetailinfo.entity.DrugDetailInfoEntity;
import com.nocdu.druginfo.drugdetailinfo.service.DrugDetailInfoService;
import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.druginfo.service.DrugInfoService;
import com.nocdu.druginfo.pillinfo.entity.PillInfoEntity;
import com.nocdu.druginfo.pillinfo.service.PillInfoService;
import com.nocdu.druginfo.requestparam.DrugSearchParamVO;
import com.nocdu.druginfo.response.Documents;
import com.nocdu.druginfo.response.DrugSearchResultVO;
import com.nocdu.druginfo.response.Meta;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @since 2023-05-23
 * @author 김봉준
 * 앱에서 호출하는 의약품 정보 API 컨트롤러
 * 스프링 부트로 변경 테스트중
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/drugsearch")
public class DrugInfoRestController {

    private static final Logger Logger = LoggerFactory.getLogger(DrugInfoRestController.class);

    private final DrugInfoService drugInfoService;
    private final DrugDetailInfoService drugDetailInfoService;
    private final PillInfoService pillInfoService;

    @GetMapping(value = "/textsearch")
    public DrugSearchResultVO getDrugTestSearchData(DrugSearchParamVO drugSearchParamVO){
        DrugSearchResultVO drugSearchResultVO = new DrugSearchResultVO();
        Page<Documents> documents = drugInfoService.selectDrugInfoSearchList(drugSearchParamVO, PageRequest.of(drugSearchParamVO.getPage(), 15));
        Meta meta = new Meta();
        meta.set_end(!documents.isLast());
        meta.setTotal_count(documents.getTotalPages());

        drugSearchResultVO.setDocuments(documents.toList());
        drugSearchResultVO.setMeta(meta);
        return drugSearchResultVO;
    }

//    @GetMapping(value = "/druginfo")
//    public DrugInfoEntity getDrugInfo(){
//        return drugInfoService.getOneDrugInfo(3L).get();
//    }
//
//    @GetMapping(value = "/drugdetailinfo")
//    public DrugDetailInfoEntity getDrugDetailInfo(){
//        return drugDetailInfoService.getOneDrugDetailInfo("195500005").get();
//    }

//    @GetMapping(value = "/pillinfo")
//    public DrugSearchResultVO getPillInfo(DrugSearchParamVO drugSearchParamVO) {
//        DrugSearchResultVO drugSearchResultVO = new DrugSearchResultVO();
//        Page<Documents> documents = drugInfoService.selectDrugInfoSearchList(drugSearchParamVO, PageRequest.of(drugSearchParamVO.getPage(), 15));
//        Meta meta = new Meta();
//        meta.set_end(!documents.isLast());
//        meta.setTotal_count(documents.getTotalPages());
//
//        drugSearchResultVO.setDocuments(documents.toList());
//        drugSearchResultVO.setMeta(meta);
//        return drugSearchResultVO;
//    }
}
