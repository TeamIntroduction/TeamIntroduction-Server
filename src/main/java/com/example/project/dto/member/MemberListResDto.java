package com.example.project.dto.member;

import com.example.project.domain.Member;
import com.example.project.domain.Position;
import lombok.Data;

@Data
public class MemberListResDto {
    private Long id;
    private String photo;
    private String name;
    private Position position;

    public MemberListResDto(Member member) {
        this.id = member.getId();
        this.photo = member.getPhoto();
        this.name = member.getName();
        this.position = member.getPosition();
    }
}
