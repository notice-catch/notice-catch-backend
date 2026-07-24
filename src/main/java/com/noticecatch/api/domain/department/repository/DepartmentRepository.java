package com.noticecatch.api.domain.department.repository;

import com.noticecatch.api.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
