package com.example.project.controller;

import com.example.project.controller.annotation.DecPathVariable;
import com.example.project.controller.annotation.DecRequestParam;
import com.example.project.controller.annotation.EncResponseBody;
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

    @EncResponseBody
    @GetMapping()
    public ResponseEntity<ResponseDto> getMemberList(@DecRequestParam String teamId) {

        return new ResponseEntity<>(ResponseDto.success(GET_MEMBER_LIST, memberService.getMemberList(teamId)), HttpStatus.OK);
    }

    @EncResponseBody
    @GetMapping("/{encryptedMemberId}")
    public ResponseEntity<ResponseDto> getMember(@DecPathVariable(name="encryptedMemberId") String memberId) throws Exception {

        return new ResponseEntity<>(ResponseDto.success(GET_MEMBER, memberService.getMember(memberId)), HttpStatus.OK);
    }
}