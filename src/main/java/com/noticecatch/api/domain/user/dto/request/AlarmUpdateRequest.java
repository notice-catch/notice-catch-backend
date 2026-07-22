package com.noticecatch.api.domain.user.dto.request;

/**
 * 부분 수정(partial update)을 지원한다. 전달되지 않은(null) 필드는 기존 값을 유지한다.
 */
public record AlarmUpdateRequest(
        Boolean isAll,
        Boolean isClosing,
        Boolean isKeyword,
        Boolean scholarship,
        Boolean extracurricular,
        Boolean academic,
        Boolean employment
) {
    public boolean isEmpty() {
        return isAll == null && isClosing == null && isKeyword == null
                && scholarship == null && extracurricular == null
                && academic == null && employment == null;
    }
}
