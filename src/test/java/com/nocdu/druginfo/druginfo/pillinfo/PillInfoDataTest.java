package com.nocdu.druginfo.druginfo.pillinfo;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.pillinfo.entity.PillInfoEntity;
import com.nocdu.druginfo.pillinfo.repository.PillInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PillInfoDataTest {
    
    @Autowired
    PillInfoRepository pillInfoRepository;

    @Test
    @DisplayName("의약품 식별 조회 테스트")
    void 의약품_식별정보_조회(){
        //given


        //when
        PillInfoEntity pillInfoEntity = pillInfoRepository.findById(2L).get();

        //then
        Assertions.assertEquals(pillInfoEntity.getEntpName(), "(주)유한양행");
    }

    @Test
    @DisplayName("의약품 상세정보 데이터 개수 테스트")
    void 의약품_식별정보_데이터_개수_테스트(){
        //given


        //when
        Long count = pillInfoRepository.countBy();

        //then
        Assertions.assertEquals(count, 25035);
    }
}
