package com.noticecatch.api.domain.support.dto.response;

import com.noticecatch.api.domain.support.entity.SupportNotice;

import java.time.LocalDateTime;

public record SupportNoticeResponse(
        Long supportNoticeId,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public static SupportNoticeResponse from(SupportNotice supportNotice) {
        return new SupportNoticeResponse(
                supportNotice.getId(),
                supportNotice.getTitle(),
                supportNotice.getContent(),
                supportNotice.getCreatedAt()
        );
    }
}
