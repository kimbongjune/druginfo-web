package com.nocdu.druginfo.batch.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * DrugDurWarning(DUR 데이터)를 기반으로 DrugSafetyInfo 테이블을 채우는 Tasklet
 * - 임부금기 -> pregnantWarning
 * - 연령금기 -> childWarning (특정 연령 이하)
 * - 노인주의 -> elderlyWarning
 * - 용량주의 -> overdoseWarning (DUR에는 없지만 로직상 필요한 경우)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SafetyInfoUpdateTasklet implements Tasklet {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Starting DrugSafetyInfo update from DrugDurWarning data...");

        // 1. DrugSafetyInfo 레코드가 없는 Drug에 대해 레코드 생성
        String initSql = """
            INSERT INTO drug_safety_info (drug_id, created_at, updated_at)
            SELECT d.id, NOW(), NOW()
            FROM drug d
            LEFT JOIN drug_safety_info s ON d.id = s.drug_id
            WHERE s.id IS NULL
        """;
        int inserted = jdbcTemplate.update(initSql);
        log.info("Initialized {} DrugSafetyInfo records.", inserted);

        // 2. 임부금기 (PREGNANT_TABOO) 업데이트
        // 여러 건일 경우 GROUP_CONCAT으로 합침
        String updatePregnantSql = """
            UPDATE drug_safety_info s
            JOIN drug d ON s.drug_id = d.id
            JOIN (
                SELECT item_seq, GROUP_CONCAT(CONCAT(type_name, ': ', prohbt_content) SEPARATOR '\n') as warning
                FROM drug_dur_warning
                WHERE warning_type = 'PREGNANT_TABOO'
                GROUP BY item_seq
            ) w ON d.item_seq = w.item_seq
            SET s.pregnant_warning = w.warning
        """;
        int updatedPregnant = jdbcTemplate.update(updatePregnantSql);
        log.info("Updated {} records with Pregnant Warnings.", updatedPregnant);

        // 3. 연령금기 (AGE_TABOO) 업데이트 -> childWarning (소아/연령 주의)
        String updateChildSql = """
            UPDATE drug_safety_info s
            JOIN drug d ON s.drug_id = d.id
            JOIN (
                SELECT item_seq, GROUP_CONCAT(CONCAT(type_name, ': ', prohbt_content) SEPARATOR '\n') as warning
                FROM drug_dur_warning
                WHERE warning_type = 'AGE_TABOO'
                GROUP BY item_seq
            ) w ON d.item_seq = w.item_seq
            SET s.child_warning = w.warning
        """;
        int updatedChild = jdbcTemplate.update(updateChildSql);
        log.info("Updated {} records with Child/Age Warnings.", updatedChild);

        // 4. 노인주의 (ELDERLY_CAUTION) 업데이트
        String updateElderlySql = """
            UPDATE drug_safety_info s
            JOIN drug d ON s.drug_id = d.id
            JOIN (
                SELECT item_seq, GROUP_CONCAT(CONCAT(type_name, ': ', prohbt_content) SEPARATOR '\n') as warning
                FROM drug_dur_warning
                WHERE warning_type = 'ELDERLY_CAUTION'
                GROUP BY item_seq
            ) w ON d.item_seq = w.item_seq
            SET s.elderly_warning = w.warning
        """;
        int updatedElderly = jdbcTemplate.update(updateElderlySql);
        log.info("Updated {} records with Elderly Warnings.", updatedElderly);

        // 5. 병용금기 업데이트 (Optional: 필요 시 interaction 필드 등에 활용 가능하지만 현재 DrugSafetyInfo에는 필드가 딱히 매핑 안됨)
        // 병용금기는 DrugInteraction 테이블에 있고, 앱에서도 거기서 조회하므로 생략.

        log.info("DrugSafetyInfo update complete.");
        return RepeatStatus.FINISHED;
    }
}
