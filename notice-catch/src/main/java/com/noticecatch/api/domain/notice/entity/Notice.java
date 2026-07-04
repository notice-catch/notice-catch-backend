package com.noticecatch.api.domain.notice.entity;

import com.noticecatch.api.domain.department.entity.Department;
import com.noticecatch.api.domain.university.entity.University;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "origin_url", length = 255)
    private String originUrl;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "deadline_at")
    private LocalDateTime deadlineAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // NoticeSummary와 1:1 관계 제어
    @OneToOne(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private NoticeSummary noticeSummary;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 1:1 대상인 AI 요약본 생성/결합 시 양방향 싱크 정합성 보장
    public void setNoticeSummary(NoticeSummary noticeSummary) {
        this.noticeSummary = noticeSummary;
        if (noticeSummary != null && noticeSummary.getNotice() != this) {
            noticeSummary.setNotice(this);
        }
    }

    // 마감 여부 판별
    public boolean isExpired() {
        if (this.deadlineAt == null) return false;
        return LocalDateTime.now().isAfter(this.deadlineAt);
    }
}
