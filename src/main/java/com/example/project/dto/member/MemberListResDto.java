package com.example.project.dto.member;

import com.example.project.domain.member.Member;
import com.example.project.domain.member.Position;
import com.example.project.utils.key.AES;
import lombok.Data;

@Data
public class MemberListResDto {
    private String id;
    private String name;
    private Position position;

    public MemberListResDto(Member member) throws Exception {
        //this.id = AES.encrypt(key, member.getId().toString());
        //this.id = member.getId();
        this.name = member.getName();
        this.position = member.getPosition();
    }
}
