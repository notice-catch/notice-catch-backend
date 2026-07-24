package com.noticecatch.api.domain.support.repository;

import com.noticecatch.api.domain.support.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findAllByCategoryOrderByCreatedAtDesc(String category);
}
