package com.noticecatch.api.domain.support.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/support")
public class SupportController implements SupportControllerDocs {
    @Override
    @GetMapping("/notices")
    public ApiResponse<Map<String, Object>> getSupportNotices(
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/faqs")
    public ApiResponse<Map<String, Object>> getFaqs(@RequestParam String category) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}