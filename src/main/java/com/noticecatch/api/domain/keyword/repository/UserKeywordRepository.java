package com.noticecatch.api.domain.keyword.repository;

import com.noticecatch.api.domain.keyword.entity.UserKeyword;
import com.noticecatch.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {
    List<UserKeyword> findAllByUser(User user);

    long countByUser(User user);

    void deleteAllByUser(User user);
}
