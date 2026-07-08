package com.noticecatch.api.domain.university.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@Tag(name = "🏫 University", description = "대학 정보 제공 및 선택 API")
public interface UniversityControllerDocs {
    @Operation(summary = "대학 목록 조회", description = "온보딩 대학 선택을 위한 전체 대학 리스트를 조회합니다.")
    ApiResponse<Map<String, Object>> getUniversities();

    @Operation(summary = "대학 선택", description = "유저의 소속 대학을 등록 및 변경합니다.")
    ApiResponse<Void> selectUniversity(
            @Parameter(description = "선택할 대학의 고유 ID", example = "1") @PathVariable Long universityId
    );
}