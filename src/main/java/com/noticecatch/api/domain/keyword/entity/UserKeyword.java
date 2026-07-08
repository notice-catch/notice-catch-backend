package com.noticecatch.api.domain.keyword.entity;

import com.noticecatch.api.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_keywords")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String keyword;

    @Column(name = "keyword_type", nullable = false, length = 50)
    private String keywordType; // RECOMMEND 또는 CUSTOM 저장

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.keywordType == null) this.keywordType = "CUSTOM"; // 기본값 지정
    }

    // 추천 키워드 여부 검증 코드
    public boolean isRecommended() {
        return "RECOMMEND".equalsIgnoreCase(this.keywordType);
    }
}