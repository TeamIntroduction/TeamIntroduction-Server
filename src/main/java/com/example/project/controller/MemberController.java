package com.example.project.controller;

import com.example.project.controller.annotation.DecPathVariable;
import com.example.project.controller.annotation.DecRequestParam;
import com.example.project.dto.ResponseDto;
import com.example.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.project.constant.SuccessResponse.GET_MEMBER;
import static com.example.project.constant.SuccessResponse.GET_MEMBER_LIST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<ResponseDto> getMemberList(@DecRequestParam String teamId) {
        System.out.println("teamId = " + teamId);
        return new ResponseEntity<>(ResponseDto.success(GET_MEMBER_LIST, memberService.getMemberList(teamId)), HttpStatus.OK);
    }

    @GetMapping("/{encryptedMemberId}")
    public ResponseEntity<ResponseDto> getMember(@DecPathVariable String memberId) throws Exception {

        return new ResponseEntity<>(ResponseDto.success(GET_MEMBER, memberService.getMember(memberId)), HttpStatus.OK);
    }
}