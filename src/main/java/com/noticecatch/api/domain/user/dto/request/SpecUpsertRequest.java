package com.noticecatch.api.domain.user.dto.request;

import java.time.LocalDate;

/**
 * 스펙 로그 추가/수정 요청 바디. 두 API의 필드 구성이 동일해서 하나로 공용 사용한다.
 * scoreOrGrade, memo는 선택 입력 항목.
 */
public record SpecUpsertRequest(
        String category,
        String title,
        String organization,
        LocalDate specDate,
        String scoreOrGrade,
        String memo
) {
}
