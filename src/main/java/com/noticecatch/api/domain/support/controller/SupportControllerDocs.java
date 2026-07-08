package com.noticecatch.api.domain.support.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Tag(name = "🛠️ Support", description = "운영 공지 및 FAQ 관리용 고객지원 API")
public interface SupportControllerDocs {

    @Operation(summary = "운영팀 공지사항 목록 조회", description = "시스템 정기점검 혹은 릴리즈 업데이트 공지사항 목록을 가져옵니다.")
    ApiResponse<Map<String, Object>> getSupportNotices(@RequestParam int page, @RequestParam int size);

    @Operation(summary = "FAQ 목록 조회", description = "선택 태그 범주에 속한 자주 묻는 질문들을 리스트업합니다.")
    ApiResponse<Map<String, Object>> getFaqs(
            @Parameter(description = "FAQ 카테고리 태그명", example = "ACCOUNT") @RequestParam String category
    );
}
