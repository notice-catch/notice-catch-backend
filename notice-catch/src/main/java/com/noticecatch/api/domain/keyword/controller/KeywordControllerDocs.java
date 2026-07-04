package com.noticecatch.api.domain.keyword.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;

@Tag(name = "🏷️ Keyword", description = "추천 키워드 제공 API")
public interface KeywordControllerDocs {
    @Operation(summary = "추천 키워드 목록 조회", description = "시스템이 기본 제공하는 추천 키워드 리스트를 가져옵니다.")
    ApiResponse<Map<String, Object>> getRecommendKeywords();
}
