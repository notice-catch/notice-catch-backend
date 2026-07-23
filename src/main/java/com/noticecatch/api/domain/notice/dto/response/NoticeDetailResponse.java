package com.noticecatch.api.domain.notice.dto.response;

import com.noticecatch.api.domain.notice.entity.Notice;
import com.noticecatch.api.domain.notice.entity.NoticeSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailResponse {

    private Long noticeId;
    private String categoryTag;
    private String title;
    private String source;
    private LocalDateTime createdAt;
    private LocalDateTime deadlineAt;
    private String content;
    private Boolean hasFiles;
    private String originalUrl;
    private AiSummaryDto aiSummary;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AiSummaryDto {
        private String eligibility;
        private String benefit;
        private String deadline;

        public static AiSummaryDto from(NoticeSummary noticeSummary) {
            if (noticeSummary == null) return null;
            return AiSummaryDto.builder()
                    .eligibility(noticeSummary.getEligibility())
                    .benefit(noticeSummary.getBenefit())
                    .deadline(noticeSummary.getDeadlineSummary())
                    .build();
        }
    }

    public static NoticeDetailResponse from(Notice notice) {
        return NoticeDetailResponse.builder()
                .noticeId(notice.getId())
                .categoryTag(notice.getCategory() != null ? notice.getCategory().getName() : null)
                .title(notice.getTitle())
                .source(notice.getDepartment() != null ? notice.getDepartment().getName() : "대학 본부")
                .createdAt(notice.getPostedAt())
                .deadlineAt(notice.getDeadlineAt())
                .content(notice.getContent())
                .hasFiles(notice.getHasAttachments())
                .originalUrl(notice.getOriginUrl())
                .aiSummary(AiSummaryDto.from(notice.getNoticeSummary()))
                .build();
    }
}