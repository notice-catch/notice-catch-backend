package com.noticecatch.api.domain.auth.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@Tag(name = "🔐 Auth", description = "인증 및 로그인 관련 API")
public interface AuthControllerDocs {
    @Operation(summary = "회원가입/로그인", description = "소셜 액세스 토큰으로 로그인 및 회원가입을 진행합니다.")
    ApiResponse<Map<String, Object>> login(@RequestBody Map<String, Object> request);

    @Operation(summary = "로그아웃", description = "유저 로그아웃 처리를 진행합니다.")
    ApiResponse<Void> logout();
}
