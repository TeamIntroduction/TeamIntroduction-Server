package com.example.project.dto.member;

import com.example.project.config.key.Aes;
import com.example.project.domain.member.Member;
import com.example.project.domain.member.Position;
import lombok.Data;

@Data
public class MemberListResDto {
    private Aes aes;
    private String id;
    private String name;
    private Position position;
    public MemberListResDto() {

    }

    public MemberListResDto(Member member, Aes aes) throws Exception {
        this.id = aes.encrypt(member.getId().toString());
        //this.id = member.getId();
        this.name = member.getName();
        this.position = member.getPosition();
    }
}
