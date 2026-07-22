package com.noticecatch.api.domain.user.exception;

import com.noticecatch.api.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SpecErrorCode implements BaseErrorCode {
    MISSING_REQUIRED_FIELD(HttpStatus.BAD_REQUEST,
            "SPEC4002", "스펙 제목, 카테고리, 취득일(활동일)은 필수 입력 항목입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "SPEC4041", "해당 스펙 로그 식별자(ID)를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,
            "SPEC4031", "해당 스펙 로그에 대한 권한이 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
