package com.example.project.domain.team;

import com.example.project.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "team")
    private final List<Member> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Team parent;

    @OneToMany(mappedBy = "parent")
    private final List<Team> children = new ArrayList<>();

    /* 연관관계 메소드 */
    public void addParent(Team parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }
}
