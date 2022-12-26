package com.example.project.repository;

import com.example.project.domain.department.Department;
import com.example.project.domain.part.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
