package com.noticecatch.api.domain.user.controller;

import com.noticecatch.api.domain.user.dto.request.SpecUpsertRequest;
import com.noticecatch.api.domain.user.dto.response.SpecResponse;
import com.noticecatch.api.domain.user.service.SpecService;
import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import com.noticecatch.api.global.apiPayload.response.ScrapPageResponse;
import com.noticecatch.api.global.resolver.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/specs")
public class SpecController implements SpecControllerDocs {

    private final SpecService specService;

    @Override
    @GetMapping
    public ApiResponse<ScrapPageResponse<SpecResponse>> getSpecs(
            @CurrentUserId Long userId,
            @RequestParam String category,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                specService.getSpecs(userId, category, sort, PageRequest.of(page, size)));
    }

    @Override
    @PostMapping
    public ApiResponse<ScrapPageResponse<SpecResponse>> addSpec(
            @CurrentUserId Long userId,
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestBody SpecUpsertRequest request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                specService.addSpec(userId, request, PageRequest.of(page, size)));
    }

    @Override
    @PutMapping("/{specId}")
    public ApiResponse<ScrapPageResponse<SpecResponse>> updateSpec(
            @CurrentUserId Long userId,
            @PathVariable Long specId,
            @RequestBody SpecUpsertRequest request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, specService.updateSpec(userId, specId, request));
    }

    @Override
    @DeleteMapping("/{specId}")
    public ApiResponse<ScrapPageResponse<SpecResponse>> deleteSpec(
            @CurrentUserId Long userId,
            @PathVariable Long specId) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, specService.deleteSpec(userId, specId));
    }
}
