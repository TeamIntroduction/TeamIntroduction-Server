package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.service.DepartmentService;
import com.example.project.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping()
    public ResponseDto getDepartments() {

        return ResponseUtil.SUCCESS("부서 리스트 조회 완료", departmentService.getDepartments());
    }
}
