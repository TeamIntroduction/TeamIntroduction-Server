package com.example.project.service;

import com.example.project.dto.department.DepartmentResDto;
import com.example.project.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentResDto> getDepartments() {

        return departmentRepository.findAll().stream()
                .map(d -> new DepartmentResDto(d))
                .collect(Collectors.toList());
    }
}
