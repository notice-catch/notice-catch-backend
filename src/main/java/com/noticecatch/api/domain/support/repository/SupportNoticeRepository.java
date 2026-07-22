package com.noticecatch.api.domain.support.repository;

import com.noticecatch.api.domain.support.entity.SupportNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportNoticeRepository extends JpaRepository<SupportNotice, Long> {
    Page<SupportNotice> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
