package com.noticecatch.api.domain.notice.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Tag(name = "📢 Notice", description = "학사 공지사항 조회, 검색, 스크랩, 캘린더 API")
public interface NoticeControllerDocs {
    @Operation(summary = "홈 공지 피드", description = "통합 공지 피드 및 카테고리 필터링 조회를 지원합니다. page=0이면 최신 동기화.")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0"),
            @Parameter(name = "size", description = "한 페이지당 출력 개수", example = "20"),
            @Parameter(name = "keyword", description = "필터 카테고리 혹은 키워드 명칭", example = "학사")
    })
    ApiResponse<Map<String, Object>> getNotices(@RequestParam int page, @RequestParam int size, @RequestParam String keyword);

    @Operation(summary = "공지 상세 보기", description = "AI 요약 내용이 결합된 공지사항 세부 내용을 가져옵니다.")
    ApiResponse<Map<String, Object>> getNoticeDetail(
            @Parameter(description = "공지사항 고유 ID", example = "42") @PathVariable Long noticeId
    );

    @Operation(summary = "키워드 검색", description = "검색어 및 정렬 기준에 맞춰 공지사항 목록을 무한 스크롤 페이징합니다.")
    ApiResponse<Map<String, Object>> searchNotices(
            @RequestParam String searchWord,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam int size
    );

    @Operation(summary = "공지 스크랩 설정/해제", description = "공지사항 단건을 스크랩하거나 취소합니다.")
    ApiResponse<Map<String, Object>> scrapNotice(
            @Parameter(description = "스크랩할 공지사항 고유 ID", example = "42") @PathVariable Long noticeId
    );

    @Operation(summary = "스크랩 목록 보기", description = "카테고리별 누적 카운팅 정보를 포함하여 스크랩 리스트를 페이징 반환합니다.")
    ApiResponse<Map<String, Object>> getScrapNotices(
            @RequestParam String categoryTag,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam int size
    );

    @Operation(summary = "캘린더 스크랩 마감 공지 날짜 조회", description = "특정 년/월에 마감 일정이 존재하는 날짜 리스트를 뽑아옵니다.")
    ApiResponse<Map<String, Object>> getCalendarDates(
            @RequestParam String year,
            @RequestParam String month
    );

    @Operation(summary = "선택한 날짜의 마감 공지 목록 조회 (기한 없음 'NONE' 포함)", description = "선택 날짜 기한에 매칭되는 마감 공지 목록을 불러옵니다.")
    ApiResponse<Map<String, Object>> getCalendarNotices(
            @Parameter(description = "조회 날짜 (yyyy-MM-dd) 또는 기한없음 조회 시 'NONE' 기입", example = "2026-07-04") @RequestParam String date,
            @RequestParam int page,
            @RequestParam int size
    );
}
