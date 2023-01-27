package com.example.project.service;

import com.example.project.dto.member.MemberListResDto;
import com.example.project.dto.member.MemberResDto;
import com.example.project.exception.err40x.InvalidException;
import com.example.project.repository.MemberRepository;
import com.example.project.utils.key.AES;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.project.constant.ErrorResponse.NOT_EXIST_ID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberListResDto> getMemberList(Long teamId) throws Exception {

        //String symmetricKey = (String)session.getAttribute("SYMMETRIC_KEY");
        //Long teamId = Long.parseLong(AES.decrypt(symmetricKey, encryptedTeamId));

        return memberRepository.findByTeamId(teamId).stream()
                .map(m -> {
                    try {
                        return new MemberListResDto(m);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    public String getMember(HttpSession session, String encryptedMemberId) throws Exception {

        String symmetricKey = (String)session.getAttribute("SYMMETRIC_KEY");
        Long memberId = Long.parseLong(AES.decrypt(symmetricKey, encryptedMemberId));
        MemberResDto memberResDto = new MemberResDto(memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidException(NOT_EXIST_ID)));

        return AES.encryptObject(symmetricKey, memberResDto);
    }
}
