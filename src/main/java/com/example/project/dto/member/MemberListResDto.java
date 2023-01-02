package com.example.project.dto.member;

import com.example.project.domain.member.Member;
import com.example.project.domain.member.Position;
import lombok.Data;

@Data
public class MemberListResDto {
    private Long id;
    private String name;
    private Position position;

    public MemberListResDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.position = member.getPosition();
    }
}
