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
                if (price.getItemSeq() != null) {
                    Optional<Drug> drugOpt = drugRepository.findByItemSeq(price.getItemSeq());
                    drugOpt.ifPresent(price::setDrug);
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
