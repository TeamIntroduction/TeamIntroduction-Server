package com.example.project.service;

import com.example.project.dto.member.MemberResDto;
import com.example.project.dto.member.MemberListResDto;
import com.example.project.exception.InvalidException;
import com.example.project.repository.MemberRepository;
import com.example.project.utils.key.AES;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberListResDto> getMemberList(Long teamId) {

        return memberRepository.findByTeamId(teamId).stream()
                .map(m -> new MemberListResDto(m))
                .collect(Collectors.toList());
    }

    public MemberResDto getMember(HttpSession session, String encryptedMemberId) throws Exception {

        String symmetricKey = (String)session.getAttribute("SYMMETRIC_KEY");

        Long memberId = Long.parseLong(AES.decrypt(symmetricKey, encryptedMemberId));

        return new MemberResDto(memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidException("멤버 조회 실패")));
    }
}
