package com.nocdu.druginfo.druginfo.restcontrollertest;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.druginfo.repository.DrugInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RestControllerTest {
    @Autowired
    DrugInfoRepository drugInfoRepository;

    @Test
    @DisplayName("의약품 기본정보 조회 테스트")
    void 의약품_기본정보_조회(){
        //given


        //when
        DrugInfoEntity drugInfoEntity = drugInfoRepository.findById(3L).get();

        //then
        Assertions.assertEquals(drugInfoEntity.getEntpName(), "동화약품(주)");
    }
}
