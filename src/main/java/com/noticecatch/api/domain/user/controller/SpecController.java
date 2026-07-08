package com.noticecatch.api.domain.user.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/specs")
public class SpecController implements SpecControllerDocs {
    @Override
    @GetMapping
    public ApiResponse<Map<String, Object>> getSpecs(
            @RequestParam String category,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PostMapping
    public ApiResponse<Map<String, Object>> addSpec(
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestBody Map<String, Object> request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PutMapping("/{specId}")
    public ApiResponse<Map<String, Object>> updateSpec(
            @PathVariable Long specId,
            @RequestBody Map<String, Object> request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @DeleteMapping("/{specId}")
    public ApiResponse<Map<String, Object>> deleteSpec(@PathVariable Long specId) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
