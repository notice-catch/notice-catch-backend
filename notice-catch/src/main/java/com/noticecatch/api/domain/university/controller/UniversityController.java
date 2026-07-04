package com.noticecatch.api.domain.university.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UniversityController implements UniversityControllerDocs {
    @Override
    @GetMapping("/universities")
    public ApiResponse<Map<String, Object>> getUniversities() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PatchMapping("/users/university/{universityId}")
    public ApiResponse<Void> selectUniversity(@PathVariable Long universityId) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
