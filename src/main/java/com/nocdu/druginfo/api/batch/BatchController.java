package com.nocdu.druginfo.api.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 배치 Job 수동 실행 Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job drugEtlJob;

    /**
     * 의약품 ETL 배치 실행
     */
    @PostMapping("/drug-etl")
    public ResponseEntity<Map<String, Object>> runDrugEtlJob() {
        try {
            log.info("Starting drugEtlJob manually...");

            JobParameters params = new JobParametersBuilder()
                    .addString("startTime", LocalDateTime.now().toString())
                    .toJobParameters();

            jobLauncher.run(drugEtlJob, params);

            return ResponseEntity.ok(Map.of(
                    "status", "STARTED",
                    "message", "Drug ETL job started successfully",
                    "startTime", LocalDateTime.now().toString()
            ));

        } catch (Exception e) {
            log.error("Failed to start drugEtlJob", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "FAILED",
                    "message", e.getMessage()
            ));
        }
    }
}
