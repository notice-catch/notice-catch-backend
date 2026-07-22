package com.noticecatch.api.domain.user.entity;

import com.noticecatch.api.domain.user.exception.SpecErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;

/**
 * {@link Spec#getCategory()}에 저장되는 문자열 값의 유효한 종류와,
 * 화면 표기용 태그 / categoryCounts 집계 키를 함께 관리한다.
 */
public enum SpecCategory {
    LICENSE("자격증", "licenseCount"),
    AWARD("수상", "awardCount"),
    ACTIVITY("대외활동", "activityCount"),
    LANGUAGE("어학", "languageCount"),
    INTERN("인턴", "internCount"),
    ETC("기타", "etcCount"),
    ;

    private final String tag;
    private final String countKey;

    SpecCategory(String tag, String countKey) {
        this.tag = tag;
        this.countKey = countKey;
    }

    public String getTag() {
        return tag;
    }

    public String getCountKey() {
        return countKey;
    }

    public static SpecCategory from(String category) {
        try {
            return SpecCategory.valueOf(category);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ProjectException(SpecErrorCode.MISSING_REQUIRED_FIELD);
        }
    }
}
