package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.service.MemberService;
import com.example.project.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public ResponseDto getMemberList(@RequestParam Long teamId) {

        return ResponseUtil.SUCCESS("멤버 리스트 조회 완료", memberService.getMemberList(teamId));
    }

    @GetMapping("/{memberId}")
    public ResponseDto getMember(@PathVariable Long memberId) {

        return ResponseUtil.SUCCESS("멤버 조회 완료", memberService.getMember(memberId));
    }
}
