package com.noticecatch.api.domain.auth.service;

import com.noticecatch.api.domain.auth.dto.request.LoginRequest;
import com.noticecatch.api.domain.auth.dto.response.LoginResponse;
import com.noticecatch.api.domain.auth.dto.response.OAuthUserInfo;
import com.noticecatch.api.domain.user.entity.User;
import com.noticecatch.api.domain.user.exception.UserErrorCode;
import com.noticecatch.api.domain.user.repository.UserRepository;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import com.noticecatch.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final OAuthClient oAuthClient;
    private final JwtProvider jwtProvider;

    public LoginResponse socialLogin(LoginRequest request) {
        // 인가 코드로 구글 Access Token 발급받기
        String socialAccessToken = oAuthClient.getAccessToken(request.getSocialType(),
                request.getAuthorizationCode());

        // 1. 발급받은 Access Token으로 유저 정보 조회
        OAuthUserInfo userInfo = oAuthClient.getUserInfo(request.getSocialType(), socialAccessToken);

        AtomicBoolean isNewUser = new AtomicBoolean(false);

        // 2. 이메일 기반 기존 유저 조회 또는 신규 회원가입
        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> {
                    isNewUser.set(true);
                    return registerNewUser(userInfo);
                });

        // 3. JWT 토큰 발급
        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(user.getNickname())
                .isNewUser(isNewUser.get())
                .build();
    }

    private User registerNewUser(OAuthUserInfo userInfo) {
        User newUser = User.builder()
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .socialProvider(userInfo.getSocialType())
                .socialId(userInfo.getSocialId())
                .build();

        return userRepository.save(newUser);
    }

    public void logout(Long userId) {
        // 유저 존재 여부 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        // 필요 시 토큰 블랙리스트/만료 처리 로직 구현
    }
}