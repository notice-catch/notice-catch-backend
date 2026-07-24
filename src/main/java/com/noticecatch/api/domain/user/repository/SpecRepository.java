package com.noticecatch.api.domain.user.repository;

import com.noticecatch.api.domain.user.entity.Spec;
import com.noticecatch.api.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecRepository extends JpaRepository<Spec, Long> {
    Page<Spec> findAllByUser(User user, Pageable pageable);

    Page<Spec> findAllByUserAndCategory(User user, String category, Pageable pageable);

    @Query("SELECT s.category AS category, COUNT(s) AS count FROM Spec s WHERE s.user = :user GROUP BY s.category")
    List<CategoryCount> countGroupedByCategory(@Param("user") User user);

    interface CategoryCount {
        String getCategory();

        long getCount();
    }
}
