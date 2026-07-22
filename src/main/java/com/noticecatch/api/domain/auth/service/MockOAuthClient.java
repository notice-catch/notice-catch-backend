package com.noticecatch.api.domain.auth.service;

import com.noticecatch.api.domain.auth.dto.response.OAuthUserInfo;
import com.noticecatch.api.domain.user.exception.UserErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local") // local 실행시에만 빈으로 등록됨
public class MockOAuthClient extends OAuthClient {

    @Override
    public OAuthUserInfo getUserInfo(String socialType, String socialToken) {
        // test-token으로 요청이 오면 외부 API 연동 없이 가짜 유저 정보 즉시 반환
        if ("test-token".equals(socialToken)) {
            return new OAuthUserInfo(
                    "mock_social_id_12345",
                    "testuser@noticecatch.com",
                    "테스트유저",
                    socialType.toUpperCase()
            );
        }

        // 그 외의 토큰값이 오면 예외 처리
        throw new ProjectException(UserErrorCode.INVALID_OAUTH_TOKEN);
    }
}
