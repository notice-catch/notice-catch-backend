package com.noticecatch.api.domain.auth.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerDocs {
    @Override
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody Map<String, Object> request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
