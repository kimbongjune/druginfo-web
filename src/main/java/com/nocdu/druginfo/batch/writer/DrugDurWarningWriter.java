package com.nocdu.druginfo.batch.writer;

import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.domain.drug.DrugDurWarning;
import com.nocdu.druginfo.domain.drug.repository.DrugDurWarningRepository;
import com.nocdu.druginfo.domain.drug.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DrugDurWarning 엔티티 DB 저장 Writer
 * (연령금기, 임부금기, 노인주의 등 통합 Writer)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugDurWarningWriter implements ItemWriter<DrugDurWarning> {

    private final DrugDurWarningRepository drugDurWarningRepository;
    private final DrugRepository drugRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends DrugDurWarning> chunk) throws Exception {
        log.debug("Writing {} drug DUR warning items", chunk.size());

        for (DrugDurWarning warning : chunk) {
            try {
                // Drug 엔티티와 연관관계 설정
                Optional<Drug> drugOpt = drugRepository.findByItemSeq(warning.getItemSeq());
                drugOpt.ifPresent(warning::setDrug);

                // Upsert
                Optional<DrugDurWarning> existing = drugDurWarningRepository
                        .findByItemSeqAndWarningTypeAndTypeCode(
                                warning.getItemSeq(), warning.getWarningType(), warning.getTypeCode());

                if (existing.isPresent()) {
                    DrugDurWarning e = existing.get();
                    e.setItemName(warning.getItemName());
                    e.setEntpName(warning.getEntpName());
                    e.setMainIngrCode(warning.getMainIngrCode());
                    e.setMainIngrKor(warning.getMainIngrKor());
                    e.setProhbtContent(warning.getProhbtContent());
                    e.setRemark(warning.getRemark());
                    e.setTypeName(warning.getTypeName());
                    if (warning.getDrug() != null) {
                        e.setDrug(warning.getDrug());
                    }
                    drugDurWarningRepository.save(e);
                } else {
                    drugDurWarningRepository.save(warning);
                }
            } catch (Exception e) {
                log.error("Error writing DUR warning {}: {}", warning.getItemSeq(), e.getMessage());
            }
        }
    }
}


