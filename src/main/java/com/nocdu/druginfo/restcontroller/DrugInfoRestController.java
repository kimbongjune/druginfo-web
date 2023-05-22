package com.nocdu.druginfo.restcontroller;

import com.nocdu.druginfo.Entity.DrugInfoEntity;
import com.nocdu.druginfo.repository.DrugInfoRepository;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/drugsearch")
public class DrugInfoRestController {

    private final DrugInfoRepository drugInfoRepository;

    @GetMapping(value = "/textsearch")
    public DrugInfoEntity getDrugTestSearchData() throws IllegalAccessException{
        return drugInfoRepository.findById(3L).orElseThrow(() -> new IllegalAccessException());
    }
}
