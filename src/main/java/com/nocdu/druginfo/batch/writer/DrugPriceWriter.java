package com.nocdu.druginfo.batch.writer;

import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.domain.drug.DrugPrice;
import com.nocdu.druginfo.domain.drug.repository.DrugPriceRepository;
import com.nocdu.druginfo.domain.drug.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DrugPrice 엔티티 DB 저장 Writer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugPriceWriter implements ItemWriter<DrugPrice> {

    private final DrugPriceRepository drugPriceRepository;
    private final DrugRepository drugRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends DrugPrice> chunk) throws Exception {
        log.debug("Writing {} drug price items", chunk.size());

        for (DrugPrice price : chunk) {
            try {
                // Drug 엔티티와 연관관계 설정
                Drug drug = null;
                
                // 1. itemSeq로 조회 (가장 정확)
                if (price.getItemSeq() != null && !price.getItemSeq().isEmpty()) {
                    Optional<Drug> drugOpt = drugRepository.findByItemSeq(price.getItemSeq());
                    if (drugOpt.isPresent()) {
                        drug = drugOpt.get();
                    }
                }
                
                // 2. itemSeq 매칭 실패 시: 제품명 + 제조사명으로 조회 (높은 정확도)
                if (drug == null && price.getItemName() != null && price.getEntpName() != null) {
                    Optional<Drug> drugOpt = drugRepository.findFirstByItemNameAndEntpName(
                        price.getItemName(), price.getEntpName());
                    if (drugOpt.isPresent()) {
                        drug = drugOpt.get();
                    }
                }

                // 3. 최후의 수단: 제품명만으로 조회
                if (drug == null && price.getItemName() != null) {
                    Optional<Drug> drugOpt = drugRepository.findFirstByItemName(price.getItemName());
                    if (drugOpt.isPresent()) {
                        drug = drugOpt.get();
                    }
                }
                
                if (drug != null) {
                    price.setDrug(drug);
                    // Critical: Drug 테이블의 정확한 itemSeq를 가져와서 Price 테이블에 채워넣음
                    // 이렇게 해야 Service에서 findByItemSeq로 조회가 가능함
                    if (price.getItemSeq() == null || price.getItemSeq().isEmpty()) {
                         price.setItemSeq(drug.getItemSeq());
                    }
                } else {
                    log.warn("Failed to find Drug linkage for Price Code: {}, Name: {}", 
                            price.getEdiCode(), price.getItemName());
                }

                // Upsert by ediCode
                Optional<DrugPrice> existing = drugPriceRepository.findByEdiCode(price.getEdiCode());

                if (existing.isPresent()) {
                    DrugPrice e = existing.get();
                    e.setItemSeq(price.getItemSeq());
                    e.setItemName(price.getItemName());
                    e.setEntpName(price.getEntpName());
                    e.setDrugAmount(price.getDrugAmount());
                    e.setUnit(price.getUnit());
                    e.setSpec(price.getSpec());
                    e.setApplyStartDate(price.getApplyStartDate());
                    e.setApplyEndDate(price.getApplyEndDate());
                    e.setPayType(price.getPayType());
                    e.setMaxPrice(price.getMaxPrice());
                    e.setGnlNmCd(price.getGnlNmCd());
                    if (price.getDrug() != null) {
                        e.setDrug(price.getDrug());
                    }
                    drugPriceRepository.save(e);
                } else {
                    drugPriceRepository.save(price);
                }
            } catch (Exception e) {
                log.error("Error writing drug price {}: {}", price.getEdiCode(), e.getMessage());
            }
        }
    }
}
