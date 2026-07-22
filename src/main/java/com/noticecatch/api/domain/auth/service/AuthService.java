package com.noticecatch.api.domain.auth.service;

import com.noticecatch.api.domain.auth.dto.request.LoginRequest;
import com.noticecatch.api.domain.auth.dto.response.LoginResponse;
import com.noticecatch.api.domain.auth.dto.response.OAuthUserInfo;
import com.noticecatch.api.domain.department.entity.Department;
import com.noticecatch.api.domain.department.repository.DepartmentRepository;
import com.noticecatch.api.domain.user.entity.User;
import com.noticecatch.api.domain.user.repository.UserRepository;
import com.noticecatch.api.global.apiPayload.code.GeneralErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final OAuthClient oAuthClient;
    // private final JwtProvider jwtProvider; // JWT 구현 시 주입받아 사용

    public LoginResponse socialLogin(LoginRequest request) {
        // 1. 소셜 로그인 토큰으로 정보 조회
        OAuthUserInfo userInfo = oAuthClient.getUserInfo(request.getSocialType(), request.getSocialToken());

        AtomicBoolean isNewUser = new AtomicBoolean(false);

        // 2. 이메일 기반 기존 유저 조회 또는 신규 회원가입
        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> {
                    isNewUser.set(true);
                    return registerNewUser(userInfo);
                });

        // 3. JWT 발급 (추후 실제 JWT 생성 메서드로 대체)
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.sample_access_token";
        String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.sample_refresh_token";

        // 4. API 명세서 구조에 맞춘 Response 반환
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(user.getNickname())
                .isNewUser(isNewUser.get())
                .build();
    }

    private User registerNewUser(OAuthUserInfo userInfo) {
        // User 엔티티의 department 필드가 nullable = false 이므로 기본 학과 설정 필요
        Department defaultDepartment = departmentRepository.findById(1L)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.INTERNAL_SERVER_ERROR));

        User newUser = User.builder()
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .socialProvider(userInfo.getSocialType())
                .socialId(userInfo.getSocialId())
                .department(defaultDepartment)
                .build();

        return userRepository.save(newUser);
    }

    public void logout(Long userId) {
        // 필요 시 RefreshToken DB 삭제 또는 Redis 블랙리스트 처리 로직 구현
    }
}