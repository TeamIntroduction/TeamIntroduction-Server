package com.example.project.dto.member;

import com.example.project.controller.annotation.Enc;
import com.example.project.domain.member.Member;
import com.example.project.domain.member.Position;
import lombok.Data;

@Data
public class MemberResDto {
    @Enc
    private String id;
    private String name;
    private String partName;
    private Position position;
    private String phoneNumber;
    private String email;

    public MemberResDto(Member member) {
        this.id = String.valueOf(member.getId());
        this.name = member.getName();
        this.partName = member.getTeam().getName();
        this.position = member.getPosition();
        this.phoneNumber = member.getPhoneNumber();
        this.email = member.getEmail();
    }
}
