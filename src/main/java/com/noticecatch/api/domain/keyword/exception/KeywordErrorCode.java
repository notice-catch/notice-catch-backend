package com.noticecatch.api.domain.keyword.exception;

import com.noticecatch.api.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum KeywordErrorCode implements BaseErrorCode {
    MAX_COUNT_EXCEEDED(HttpStatus.BAD_REQUEST,
            "KEYWORD4001", "관심 키워드는 최대 10개까지만 등록할 수 있습니다."),
    CUSTOM_LENGTH_EXCEEDED(HttpStatus.BAD_REQUEST,
            "KEYWORD4002", "직접 입력 키워드는 공백 포함 최대 10자 이하여야 합니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
