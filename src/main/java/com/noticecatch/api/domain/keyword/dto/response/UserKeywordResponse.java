package com.noticecatch.api.domain.keyword.dto.response;

import com.noticecatch.api.domain.keyword.entity.UserKeyword;

public record UserKeywordResponse(String keyword, String keywordType) {
    public static UserKeywordResponse from(UserKeyword userKeyword) {
        return new UserKeywordResponse(userKeyword.getKeyword(), userKeyword.getKeywordType());
    }
}
