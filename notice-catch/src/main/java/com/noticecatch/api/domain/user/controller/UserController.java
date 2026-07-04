package com.noticecatch.api.domain.user.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController implements UserControllerDocs {
    @Override
    @PatchMapping("/users/department/{departmentId}")
    public ApiResponse<Void> setupOnboardingProfile(
            @PathVariable Long departmentId,
            @RequestBody Map<String, Object> request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/users/profile")
    public ApiResponse<Map<String, Object>> getProfile() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PatchMapping("/users/profile")
    public ApiResponse<Map<String, Object>> updateProfile(@RequestBody Map<String, Object> request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/users/alarm")
    public ApiResponse<Map<String, Object>> getAlarmSettings() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PatchMapping("/users/alarm")
    public ApiResponse<Map<String, Object>> updateAlarmSettings(@RequestBody Map<String, Object> request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/users/keywords")
    public ApiResponse<Map<String, Object>> getUserKeywords() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PutMapping("/users/keywords")
    public ApiResponse<Map<String, Object>> updateKeywords(@RequestBody Map<String, Object> request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
