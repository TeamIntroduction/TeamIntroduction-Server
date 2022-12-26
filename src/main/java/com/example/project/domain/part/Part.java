package com.example.project.domain.part;

import com.example.project.domain.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartName name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_ID")
    private Team team;
}
