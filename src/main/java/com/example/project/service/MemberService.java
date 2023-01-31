package com.example.project.service;

import com.example.project.config.key.Aes;
import com.example.project.dto.member.MemberListResDto;
import com.example.project.dto.member.MemberResDto;
import com.example.project.exception.err40x.InvalidException;
import com.example.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.project.constant.ErrorResponse.NOT_EXIST_ID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final Aes aes;
    private final MemberRepository memberRepository;


    public List<MemberListResDto> getMemberList(String teamId) {

        return memberRepository.findByTeamId(Long.parseLong(teamId)).stream()
                .map(m -> new MemberListResDto(m))
                .collect(Collectors.toList());
    }

    public MemberResDto getMember(String memberId) throws Exception {

        MemberResDto memberResDto = new MemberResDto(memberRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> new InvalidException(NOT_EXIST_ID)));

        return memberResDto;
    }
}
