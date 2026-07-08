package com.noticecatch.api.domain.user.entity;

import com.noticecatch.api.domain.department.entity.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
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

    private Integer grade; // 학년

    @Column(name = "all_notification")
    @ColumnDefault("true")
    private Boolean allNotification;

    @Column(name = "closing_notification")
    @ColumnDefault("true")
    private Boolean closingNotification;

    @Column(name = "keyword_notification")
    @ColumnDefault("true")
    private Boolean keywordNotification;

    @ColumnDefault("true")
    private Boolean scholarship;

    @Column(name = "extracurricular")
    @ColumnDefault("true")
    private Boolean extracurricular;

    @ColumnDefault("true")
    private Boolean academic;

    @ColumnDefault("true")
    private Boolean employment;

    @Column(name = "push_token", length = 500)
    private String pushToken; // FCM 기기 토큰 스트링

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

    // FCM 디바이스 토큰 갱신 (로그인/앱 재설치 시 호출)
    public void updatePushToken(String newPushToken) {
        this.pushToken = newPushToken;
    }

    //전체 알람 설정
    public void toggleAllNotification(boolean status) {
        this.allNotification = status;
    }

    //키워드별 알람 설정
    public void updateCategoryNotifications(boolean scholarship, boolean extracurricular, boolean academic, boolean employment) {
        this.scholarship = scholarship;
        this.extracurricular = extracurricular;
        this.academic = academic;
        this.employment = employment;
    }

    public void changeDepartment(Department department) {
        this.department = department;
    }

    public void updateProfile(String nickname, Integer grade) {
        this.nickname = nickname;
        this.grade = grade;
    }

    public void withdraw() {
        this.status = "WITHDRAWN";
    }
}
