package com.example.project.domain.department;

import com.example.project.domain.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "department")
    private List<Team> team;
}
