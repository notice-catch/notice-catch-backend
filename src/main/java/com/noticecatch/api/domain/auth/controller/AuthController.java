package com.noticecatch.api.domain.auth.controller;

import com.noticecatch.api.domain.auth.dto.request.LoginRequest;
import com.noticecatch.api.domain.auth.dto.response.LoginResponse;
import com.noticecatch.api.domain.auth.service.AuthService;
import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, authService.socialLogin(request));
    }

    @Override
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
