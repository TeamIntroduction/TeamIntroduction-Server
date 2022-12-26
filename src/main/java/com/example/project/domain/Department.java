package com.example.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DepartmentName name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
}
