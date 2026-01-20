package com.nocdu.druginfo.api.drug;

import com.nocdu.druginfo.api.drug.request.DrugSearchRequest;
import com.nocdu.druginfo.api.drug.request.PillSearchRequest;
import com.nocdu.druginfo.api.drug.response.DrugSearchResponse;
import com.nocdu.druginfo.api.drug.response.PageResponse;
import com.nocdu.druginfo.application.drug.DrugSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 의약품 검색 API Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/drugs")
@RequiredArgsConstructor
@Tag(name = "Drug Search API", description = "의약품 검색 API")
public class DrugSearchController {

    private final DrugSearchService drugSearchService;

    private static final int DEFAULT_PAGE_SIZE = 15;

    /**
     * 의약품 통합 검색 (이름, 제조사, 효능)
     */
    @GetMapping
    @Operation(summary = "의약품 통합 검색", description = "의약품명, 제조사, 효능으로 통합 검색")
    public ResponseEntity<PageResponse<DrugSearchResponse>> searchDrugs(
            @Parameter(description = "검색어 (의약품명, 제조사, 효능 통합 검색)")
            @RequestParam(required = false) String query,
            @Parameter(description = "의약품명")
            @RequestParam(required = false) String itemName,
            @Parameter(description = "제조사명")
            @RequestParam(required = false) String entpName,
            @Parameter(description = "효능")
            @RequestParam(required = false) String efficacy,
            @Parameter(description = "분류명")
            @RequestParam(required = false) String className,
            @Parameter(description = "전문/일반 (전문의약품, 일반의약품)")
            @RequestParam(required = false) String etcOtcName,
            @Parameter(description = "페이지 번호 (0부터 시작)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기")
            @RequestParam(defaultValue = "15") int size) {

        log.info("Drug search request - query: {}, page: {}, size: {}", query, page, size);

        DrugSearchRequest request = DrugSearchRequest.builder()
                .query(query)
                .itemName(itemName)
                .entpName(entpName)
                .efficacy(efficacy)
                .className(className)
                .etcOtcName(etcOtcName)
                .build();

        Pageable pageable = PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE);
        Page<DrugSearchResponse> result = drugSearchService.searchDrugs(request, pageable);

        return ResponseEntity.ok(PageResponse.from(result));
    }

    /**
     * 낱알 식별 검색 (모양, 색상, 문구, 제형)
     */
    @GetMapping("/identification")
    @Operation(summary = "낱알 식별 검색", description = "의약품 모양, 색상, 식별문구, 제형으로 검색")
    public ResponseEntity<PageResponse<DrugSearchResponse>> searchByIdentification(
            @Parameter(description = "모양 (원형, 타원형, 장방형 등)")
            @RequestParam(required = false) String shape,
            @Parameter(description = "색상 (앞/뒤 통합 검색)")
            @RequestParam(required = false) String color,
            @Parameter(description = "앞면 색상")
            @RequestParam(required = false) String colorFront,
            @Parameter(description = "뒷면 색상")
            @RequestParam(required = false) String colorBack,
            @Parameter(description = "앞면 식별문구")
            @RequestParam(required = false) String printFront,
            @Parameter(description = "뒷면 식별문구")
            @RequestParam(required = false) String printBack,
            @Parameter(description = "제형 (정제, 캡슐 등)")
            @RequestParam(required = false) String formCodeName,
            @Parameter(description = "분할선")
            @RequestParam(required = false) String line,
            @Parameter(description = "분류명")
            @RequestParam(required = false) String className,
            @Parameter(description = "페이지 번호 (0부터 시작)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기")
            @RequestParam(defaultValue = "15") int size) {

        log.info("Pill identification search - shape: {}, color: {}, page: {}", shape, color, page);

        PillSearchRequest request = PillSearchRequest.builder()
                .shape(shape)
                .color(color)
                .colorFront(colorFront)
                .colorBack(colorBack)
                .printFront(printFront)
                .printBack(printBack)
                .formCodeName(formCodeName)
                .line(line)
                .className(className)
                .build();

        Pageable pageable = PageRequest.of(page, size > 0 ? size : DEFAULT_PAGE_SIZE);
        Page<DrugSearchResponse> result = drugSearchService.searchByIdentification(request, pageable);

        return ResponseEntity.ok(PageResponse.from(result));
    }

    /**
     * 의약품 상세 조회 (품목기준코드)
     */
    @GetMapping("/{itemSeq}")
    @Operation(summary = "의약품 상세 조회", description = "품목기준코드로 의약품 상세 정보 조회")
    public ResponseEntity<DrugSearchResponse> getDrugDetail(
            @Parameter(description = "품목기준코드")
            @PathVariable String itemSeq) {

        log.info("Get drug detail - itemSeq: {}", itemSeq);

        return drugSearchService.getDrugByItemSeq(itemSeq)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 의약품 통계 조회
     */
    @GetMapping("/stats")
    @Operation(summary = "의약품 통계", description = "전체 의약품 수 조회")
    public ResponseEntity<StatsResponse> getStats() {
        long count = drugSearchService.countDrugs();
        return ResponseEntity.ok(new StatsResponse(count));
    }

    /**
     * 통계 응답
     */
    public record StatsResponse(long totalDrugs) {}

    /**
     * 의약품 병용금기 정보 조회
     */
    @GetMapping("/{itemSeq}/interactions")
    @Operation(summary = "병용금기 정보 조회", description = "품목기준코드로 병용금기 약물 목록 조회")
    public ResponseEntity<java.util.List<DrugSearchResponse.InteractionInfo>> getInteractions(
            @Parameter(description = "품목기준코드")
            @PathVariable String itemSeq) {

        log.info("Get interactions - itemSeq: {}", itemSeq);
        var interactions = drugSearchService.getInteractionsByItemSeq(itemSeq);
        return ResponseEntity.ok(interactions);
    }

    /**
     * 의약품 DUR 경고 정보 조회
     */
    @GetMapping("/{itemSeq}/dur-warnings")
    @Operation(summary = "DUR 경고 정보 조회", description = "품목기준코드로 DUR 경고(연령금기, 임부금기, 노인주의 등) 조회")
    public ResponseEntity<java.util.List<DrugSearchResponse.DurWarningInfo>> getDurWarnings(
            @Parameter(description = "품목기준코드")
            @PathVariable String itemSeq) {

        log.info("Get DUR warnings - itemSeq: {}", itemSeq);
        var warnings = drugSearchService.getDurWarningsByItemSeq(itemSeq);
        return ResponseEntity.ok(warnings);
    }

    /**
     * 의약품 약가 정보 조회
     */
    @GetMapping("/{itemSeq}/price")
    @Operation(summary = "약가 정보 조회", description = "품목기준코드로 약가(보험급여) 정보 조회")
    public ResponseEntity<DrugSearchResponse.PriceInfo> getPriceInfo(
            @Parameter(description = "품목기준코드")
            @PathVariable String itemSeq) {

        log.info("Get price info - itemSeq: {}", itemSeq);
        return drugSearchService.getPriceByItemSeq(itemSeq)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
