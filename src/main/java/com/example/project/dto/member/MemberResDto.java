package com.example.project.dto.member;

import com.example.project.domain.Member;
import com.example.project.domain.Position;
import com.example.project.dto.part.PartResDto;
import lombok.Data;

@Data
public class MemberResDto {
    private Long id;
    private String name;
    private PartResDto part;
    private Position position;
    private String phoneNumber;
    private String email;
    private String photo;

    public MemberResDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.part = new PartResDto(member.getPart());
        this.position = member.getPosition();
        this.phoneNumber = member.getPhoneNumber();
        this.email = member.getEmail();
        this.photo = member.getPhoto();
    }
}
