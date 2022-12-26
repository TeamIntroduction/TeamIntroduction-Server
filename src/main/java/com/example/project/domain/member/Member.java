package com.example.project.domain.member;

import com.example.project.domain.department.Department;
import com.example.project.domain.part.Part;
import com.example.project.domain.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Part part;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    private String photo;
}
