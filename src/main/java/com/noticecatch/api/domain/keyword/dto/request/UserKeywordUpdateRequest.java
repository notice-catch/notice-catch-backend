package com.noticecatch.api.domain.keyword.dto.request;

import java.util.List;

public record UserKeywordUpdateRequest(List<KeywordItem> keywords) {

    public boolean isEmpty() {
        return keywords == null || keywords.isEmpty();
    }

    public record KeywordItem(String keyword, String keywordType) {
    }
}
