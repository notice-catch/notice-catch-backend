package com.noticecatch.api.domain.keyword.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class KeywordController implements KeywordControllerDocs {
    @Override
    @GetMapping("/keywords/recommend")
    public ApiResponse<Map<String, Object>> getRecommendKeywords() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
