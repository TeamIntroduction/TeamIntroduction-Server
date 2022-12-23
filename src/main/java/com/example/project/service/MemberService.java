package com.example.project.service;

import com.example.project.dto.member.MemberResDto;
import com.example.project.dto.member.MemberListResDto;
import com.example.project.exception.InvalidException;
import com.example.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberListResDto> getMemberList(Long partId) {

        return memberRepository.findByPartId(partId).stream()
                .map(m -> new MemberListResDto(m))
                .collect(Collectors.toList());
    }

    public MemberResDto getMember(Long memberId) {

        return new MemberResDto(memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidException("멤버 조회 실패")));
    }
}
