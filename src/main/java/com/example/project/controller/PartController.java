package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.service.PartService;
import com.example.project.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parts")
public class PartController {

    private final PartService partService;

    @GetMapping()
    public ResponseDto getParts() {

        return ResponseUtil.SUCCESS("부서 리스트 조회 완료", partService.getParts());
    }
}
