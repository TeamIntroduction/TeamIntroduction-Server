package com.example.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamName name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Part part;
}
