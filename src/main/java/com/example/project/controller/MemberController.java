package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.example.project.constant.SuccessResponse.GET_MEMBER;
import static com.example.project.constant.SuccessResponse.GET_MEMBER_LIST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<ResponseDto> getMemberList(HttpSession session, @RequestParam String teamId) throws Exception {

        return new ResponseEntity<>(ResponseDto.success(GET_MEMBER_LIST, memberService.getMemberList(session, teamId)), HttpStatus.OK);
    }

    @GetMapping("/{encryptedMemberId}")
    public ResponseEntity<ResponseDto> getMember(HttpSession session, @PathVariable String encryptedMemberId) throws Exception {

        return new ResponseEntity<>(ResponseDto.success(GET_MEMBER, memberService.getMember(session, encryptedMemberId)), HttpStatus.OK);
    }
}
