package com.noticecatch.api.domain.auth.service;

import com.noticecatch.api.domain.auth.dto.request.LoginRequest;
import com.noticecatch.api.domain.auth.dto.response.LoginResponse;
import com.noticecatch.api.domain.auth.dto.response.OAuthUserInfo;
import com.noticecatch.api.domain.user.entity.User;
import com.noticecatch.api.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OAuthClient oAuthClient;

    @Nested
    @DisplayName("소셜 로그인 테스트")
    class SocialLoginTest {

        @Test
        @DisplayName("신규 유저는 회원가입 후 isNewUser가 true로 반환된다")
        void socialLogin_newUser_success() {
            // given
            LoginRequest request = new LoginRequest("KAKAO", "test-token");
            OAuthUserInfo mockUserInfo = new OAuthUserInfo(
                    "12345",
                    "newuser@noticecatch.com",
                    "신규유저",
                    "KAKAO"
            );

            // OAuthClientMock 동작 정의
            given(oAuthClient.getUserInfo(request.getSocialType(), request.getSocialToken()))
                    .willReturn(mockUserInfo);

            // 기존 유저 조회가 안되는 상황 (Optional.empty())
            given(userRepository.findByEmail(mockUserInfo.getEmail()))
                    .willReturn(Optional.empty());

            // newUser 저장 시 mock 객체 리턴
            User savedUser = User.builder()
                    .email(mockUserInfo.getEmail())
                    .nickname(mockUserInfo.getNickname())
                    .socialProvider(mockUserInfo.getSocialType())
                    .socialId(mockUserInfo.getSocialId())
                    .build(); // department는 null 상태

            given(userRepository.save(any(User.class))).willReturn(savedUser);

            // when
            LoginResponse response = authService.socialLogin(request);

            // then
            assertThat(response).isNotNull();
            assertThat(response.getNickname()).isEqualTo("신규유저");
            assertThat(response.isNewUser()).isTrue();

            // registerNewUser 가 호출되어 save()가 수행되었는지 검증
            verify(userRepository).save(any(User.class));
        }

        @Test
        @DisplayName("기존 유저는 새로 가입하지 않고 isNewUser가 false로 반환된다")
        void socialLogin_existingUser_success() {
            // given
            LoginRequest request = new LoginRequest("KAKAO", "existing-token");
            OAuthUserInfo mockUserInfo = new OAuthUserInfo(
                    "67890",
                    "existing@noticecatch.com",
                    "기존유저",
                    "KAKAO"
            );

            User existingUser = User.builder()
                    .email(mockUserInfo.getEmail())
                    .nickname(mockUserInfo.getNickname())
                    .socialProvider(mockUserInfo.getSocialType())
                    .socialId(mockUserInfo.getSocialId())
                    .build();

            given(oAuthClient.getUserInfo(request.getSocialType(), request.getSocialToken()))
                    .willReturn(mockUserInfo);

            // 기존 유저가 조회되는 상황
            given(userRepository.findByEmail(mockUserInfo.getEmail()))
                    .willReturn(Optional.of(existingUser));

            // when
            LoginResponse response = authService.socialLogin(request);

            // then
            assertThat(response).isNotNull();
            assertThat(response.getNickname()).isEqualTo("기존유저");
            assertThat(response.isNewUser()).isFalse();

            // 신규 유저가 아니므로 save()가 절대 호출되지 않아야 함
            verify(userRepository, never()).save(any(User.class));
        }
    }
}