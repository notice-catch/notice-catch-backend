package com.noticecatch.api.domain.notice.repository;

import com.noticecatch.api.domain.notice.entity.UserNotice;
import com.noticecatch.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNoticeRepository extends JpaRepository<UserNotice, Long> {
    long countByUserAndIsScrapped(User user, boolean isScrapped);

    long countByUserAndIsRead(User user, boolean isRead);
}
