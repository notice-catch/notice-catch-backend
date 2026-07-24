package com.noticecatch.api.domain.user.exception;

import com.noticecatch.api.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST,
            "USER4003", "지원하지 않는 소셜 로그인 제공자입니다."),
    INVALID_OAUTH_TOKEN(HttpStatus.UNAUTHORIZED,
            "USER4011", "유효하지 않거나 만료된 소셜 토큰입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "USER4041", "존재하지 않거나 이미 탈퇴한 회원의 프로필입니다."),
    INVALID_UNIVERSITY_OR_DEPARTMENT(HttpStatus.BAD_REQUEST,
            "USER4002", "선택하신 학교 또는 학과 정보가 올바르지 않거나 존재하지 않습니다."),
    ALARM_SETTING_REQUIRED(HttpStatus.BAD_REQUEST,
            "ALARM4001", "알림 설정 값은 필수입니다."),
    ALARM_SUB_SETTING_CONFLICT(HttpStatus.BAD_REQUEST,
            "ALARM4002", "전체 알림이 꺼진 상태(isAll=false)에서는 하위 알림 설정을 활성화할 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
