package com.nocdu.druginfo.restcontroller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.nocdu.druginfo.drugdetailinfo.service.DrugDetailInfoService;
import com.nocdu.druginfo.druginfo.service.DrugInfoService;
import com.nocdu.druginfo.pillinfo.service.PillInfoService;
import com.nocdu.druginfo.request.DrugSearchRequestParamDto;
import com.nocdu.druginfo.response.Documents;
import com.nocdu.druginfo.response.DrugSearchResultDto;
import com.nocdu.druginfo.response.Meta;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @since 2023-05-23
 * @author 김봉준
 * 앱에서 호출하는 의약품 정보 API 컨트롤러
 * 스프링 부트로 변경 테스트중
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/DrugInfo/drugsearch")
public class DrugInfoRestController {

    private static final Logger Logger = LoggerFactory.getLogger(DrugInfoRestController.class);

    //의약품 정보 서비스 객체
    private final DrugInfoService drugInfoService;
//    private final DrugDetailInfoService drugDetailInfoService;
//    private final PillInfoService pillInfoService;


    /**
     * @since 2023-05-23
     * @author 김봉준
     * @param drugSearchRequestParamDto DrugSearchRequestParamDto
     */
    @GetMapping(value = "/textsearch")
    public DrugSearchResultDto getDrugTestSearchData(DrugSearchRequestParamDto drugSearchRequestParamDto){
        Logger.info("리퀘스트 발생");
        Logger.info(drugSearchRequestParamDto.toString());
        DrugSearchResultDto drugSearchResultDto = new DrugSearchResultDto();
        Page<Documents> documents = drugInfoService.selectDrugInfoSearchList(drugSearchRequestParamDto, PageRequest.of(drugSearchRequestParamDto.getPage(), 15));
        Meta meta = new Meta();
        meta.set_end(!documents.isLast());
        meta.setTotal_count(documents.getTotalPages());
        Logger.info("데이터 개수{}",documents.getTotalPages());

        drugSearchResultDto.setDocuments(documents.toList());
        drugSearchResultDto.setMeta(meta);
        return drugSearchResultDto;
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
