package com.nocdu.druginfo.druginfo.drugdetailinfo;

import com.nocdu.druginfo.drugdetailinfo.entity.DrugDetailInfoEntity;
import com.nocdu.druginfo.drugdetailinfo.repository.DrugDetailInfoRepository;
import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DrugDetailInfoDataTest {

    @Autowired
    DrugDetailInfoRepository drugDetailInfoRepository;

    @Test
    @DisplayName("의약품 상세정보 조회 테스트")
    void 의약품_기본정보_조회(){
        //given


        //when
        DrugDetailInfoEntity drugDetailInfoEntity = drugDetailInfoRepository.findById("195500005").get();

        //then
        Assertions.assertEquals(drugDetailInfoEntity.getEntpName(), "제이더블유중외제약(주)");
    }

    @Test
    @DisplayName("의약품 상세정보 데이터 개수 테스트")
    void 의약품_상세정보_데이터_개수_테스트(){
        //given


        //when
        Long count = drugDetailInfoRepository.countBy();

        //then
        Assertions.assertEquals(count, 51132);
    }
}
