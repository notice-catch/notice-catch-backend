package com.noticecatch.api.domain.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice_summaries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NoticeSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false, unique = true)
    private Notice notice;

    @Column(length = 255)
    private String eligibility;

    @Column(length = 255)
    private String benefit;

    @Column(name = "deadline_summary", length = 255)
    private String deadlineSummary;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 내부 무한루프 방지 setter
    protected void setNotice(Notice notice) {
        this.notice = notice;
    }
}
