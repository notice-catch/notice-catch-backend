package com.noticecatch.api.domain.user.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "📝 Spec Log", description = "유저 개인 취업 준비 스펙 CRUD 관리 API")
public interface SpecControllerDocs {

    @Operation(summary = "스펙 로그 목록 조회", description = "유형별 누적 통계 헤더와 세부 이력 목록 데이터를 페이징하여 보여줍니다.")
    ApiResponse<Map<String, Object>> getSpecs(
            @RequestParam String category,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam int size
    );

    @Operation(summary = "스펙 로그 추가", description = "새 기록 이력을 기입하고 갱신 피드를 출력합니다.")
    ApiResponse<Map<String, Object>> addSpec(@RequestParam int page, @RequestParam int size, @RequestBody Map<String, Object> request);

    @Operation(summary = "스펙 로그 수정", description = "특정 고유번호의 취업 스펙 기록 사안을 수정 업데이트합니다.")
    ApiResponse<Map<String, Object>> updateSpec(
            @Parameter(description = "수정하려는 스펙 고유 ID", example = "104") @PathVariable Long specId,
            @RequestBody Map<String, Object> request
    );

    @Operation(summary = "스펙 로그 삭제", description = "단일 스펙을 삭제 후 정리된 최신 현황 목록을 재반환합니다.")
    ApiResponse<Map<String, Object>> deleteSpec(
            @Parameter(description = "삭제하려는 스펙 고유 ID", example = "104") @PathVariable Long specId
    );
}
