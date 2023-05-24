package com.nocdu.druginfo.druginfo.druginfotest;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.druginfo.repository.DrugInfoRepository;
import com.nocdu.druginfo.request.DrugSearchRequestParamDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DrugInfoDataTest {
    @Autowired
    DrugInfoRepository drugInfoRepository;

    DrugSearchRequestParamDto drugSearchRequestParamDto;

    @BeforeEach
    void DTO클래스_인스턴스화(){
        drugSearchRequestParamDto = new DrugSearchRequestParamDto();
    }

    @AfterEach
    void DTO클래스_초기화(){
        drugSearchRequestParamDto = null;
    }

    @Test
    @DisplayName("의약품 기본정보 조회 테스트")
    void 의약품_기본정보_조회(){
        //given


        //when
        DrugInfoEntity drugInfoEntity = drugInfoRepository.findById(3L).get();

        //then
        Assertions.assertEquals(drugInfoEntity.getEntpName(), "동화약품(주)");
    }

    @Test
    @DisplayName("의약품 기본정보 데이터 개수 테스트")
    void 의약품_기본정보_데이터_개수_테스트(){
        //given


        //when
        Long count = drugInfoRepository.countBy();

        //then
        Assertions.assertEquals(count, 4334);
    }

    @Test
    @DisplayName("의약품 API 페이징 처리 테스트")
    void 의약품_API데이터_페이징_테스트(){
        //given
        int pageCount = 15;
        Pageable pageable = PageRequest.of(pageCount, 15);
        //when
        int count = drugInfoRepository.selectDrugInfoSearchList(drugSearchRequestParamDto, pageable).getSize();

        //then
        Assertions.assertEquals(count, 15);
    }

    @Test
    @DisplayName("의약품 API 파라미터 없이 데이터 테스트")
    void 의약품_API데이터_파라미터_없이_테스트(){
        //given

        //when
        int count = drugInfoRepository.selectDrugInfoSearchListNotPage(drugSearchRequestParamDto).size();

        //then
        Assertions.assertEquals(count, 4336);
    }

    @Test
    @DisplayName("의약품 API 의약품 이름 파라미터 데이터 테스트")
    void 의약품_API데이터_의약품_이름_파라미터_테스트(){
        //given
        drugSearchRequestParamDto.setQuery("아스피린");

        //when
        int count = drugInfoRepository.selectDrugInfoSearchListNotPage(drugSearchRequestParamDto).size();

        //then
        Assertions.assertEquals(count, 53);
    }

    @Test
    @DisplayName("의약품 API 의약품 색깔 파라미터 데이터 테스트")
    void 의약품_API데이터_의약품_색깔_파라미터_테스트(){
        //given
        drugSearchRequestParamDto.setColorClass("파랑");

        //when
        int count = drugInfoRepository.selectDrugInfoSearchListNotPage(drugSearchRequestParamDto).size();

        //then
        Assertions.assertEquals(count, 188);
    }

    @Test
    @DisplayName("의약품 API 의약품 전면 식별문구 파라미터 데이터 테스트")
    void 의약품_API데이터_의약품_전면_식별문구_파라미터_테스트(){
        //given
        drugSearchRequestParamDto.setPrintFront("p");

        //when
        int count = drugInfoRepository.selectDrugInfoSearchListNotPage(drugSearchRequestParamDto).size();

        //then
        Assertions.assertEquals(count, 493);
    }

    @Test
    @DisplayName("의약품 API 의약품 후면 식별문구 파라미터 데이터 테스트")
    void 의약품_API데이터_의약품_후면_식별문구_파라미터_테스트(){
        //given
        drugSearchRequestParamDto.setPrintBack("p");

        //when
        int count = drugInfoRepository.selectDrugInfoSearchListNotPage(drugSearchRequestParamDto).size();

        //then
        Assertions.assertEquals(count, 44);
    }

    @Test
    @DisplayName("의약품 API 의약품 구분선 파라미터 데이터 테스트")
    void 의약품_API데이터_의약품_구분선_파라미터_테스트(){
        //given
        drugSearchRequestParamDto.setLine("-");

        //when
        int count = drugInfoRepository.selectDrugInfoSearchListNotPage(drugSearchRequestParamDto).size();

        //then
        Assertions.assertEquals(count, 389);
    }

    @Test
    @DisplayName("의약품 API 의약품 장방형 파라미터 데이터 테스트")
    void 의약품_API데이터_의약품_장방형_파라미터_테스트(){
        //given
        drugSearchRequestParamDto.setDosageForm("캡슐");

        //when
        int count = drugInfoRepository.selectDrugInfoSearchListNotPage(drugSearchRequestParamDto).size();

        //then
        Assertions.assertEquals(count, 1054);
    }

    @Test
    @DisplayName("의약품 API 의약품 복합 파라미터 데이터 테스트")
    void 의약품_API데이터_의약품_복합_파라미터_테스트(){
        //given
        drugSearchRequestParamDto.setQuery("아스피린");
        drugSearchRequestParamDto.setDosageForm("캡슐");

        //when
        int count = drugInfoRepository.selectDrugInfoSearchListNotPage(drugSearchRequestParamDto).size();

        //then
        Assertions.assertEquals(count, 3);
    }
}
