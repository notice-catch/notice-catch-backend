package com.noticecatch.api.domain.user.entity;

import com.noticecatch.api.domain.department.entity.Department;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String nickname;

    @Column(name = "social_provider", length = 255)
    private String socialProvider;

    @Column(name = "social_id", length = 255)
    private String socialId;

    @Column(length = 255)
    private String role;

    @Column(length = 255)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) this.role = "USER";
        if (this.status == null) this.status = "ACTIVE";
    }

    // 학과 변경
    public void changeDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("변경할 학과 정보가 존재하지 않습니다.");
        }
        this.department = department;
    }

    // 프로필 수정
    public void updateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 비어있을 수 없습니다.");
        }
        this.nickname = nickname;
    }

    // 회원 탈퇴 처리
    public void withdraw() {
        this.status = "WITHDRAWN";
    }
}
