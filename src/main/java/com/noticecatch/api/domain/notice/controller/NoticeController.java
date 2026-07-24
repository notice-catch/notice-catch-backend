package com.noticecatch.api.domain.notice.controller;

import com.noticecatch.api.domain.notice.dto.response.NoticeDetailResponse;
import com.noticecatch.api.domain.notice.service.NoticeService;
import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController implements NoticeControllerDocs {

    private final NoticeService noticeService;

    @Override
    @GetMapping
    public ApiResponse<Map<String, Object>> getNotices(
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/{noticeId}")
    public ApiResponse<NoticeDetailResponse> getNoticeDetail(@PathVariable Long noticeId) {
        NoticeDetailResponse response = noticeService.getNoticeDetail(noticeId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    @Override
    @GetMapping("/search")
    public ApiResponse<Map<String, Object>> searchNotices(
            @RequestParam String searchWord,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PostMapping("/{noticeId}/scrap")
    public ApiResponse<Map<String, Object>> scrapNotice(@PathVariable Long noticeId) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/scraps")
    public ApiResponse<Map<String, Object>> getScrapNotices(
            @RequestParam String categoryTag,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/calendar/dates")
    public ApiResponse<Map<String, Object>> getCalendarDates(
            @RequestParam String year,
            @RequestParam String month) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/calendar")
    public ApiResponse<Map<String, Object>> getCalendarNotices(
            @RequestParam String date,
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/calendar/no-deadline")
    public ApiResponse<Map<String, Object>> getNoDeadlineNotices(
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}