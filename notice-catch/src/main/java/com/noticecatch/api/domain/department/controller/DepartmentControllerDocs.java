package com.noticecatch.api.domain.department.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Tag(name = "🎓 Department", description = "학과 조회 및 검색 API")
public interface DepartmentControllerDocs {
    @Operation(summary = "학과 목록 조회 및 검색", description = "특정 대학에 개설된 학과 목록을 검색 키워드 기반으로 조회합니다.")
    ApiResponse<Map<String, Object>> getDepartments(
            @Parameter(description = "조회 대상 대학의 고유 ID", example = "1") @PathVariable Long universityId,
            @Parameter(description = "학과 검색어 (필터 필드)", example = "컴퓨터") @RequestParam(name = "keyword", required = false) String keyword
    );
}
