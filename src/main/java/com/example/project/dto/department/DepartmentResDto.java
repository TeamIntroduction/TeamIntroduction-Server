package com.example.project.dto.department;

import com.example.project.domain.department.Department;
import com.example.project.domain.department.DepartmentName;
import com.example.project.dto.team.TeamResDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DepartmentResDto {

    private Long id;
    private DepartmentName name;
    private List<TeamResDto> children;

    public DepartmentResDto(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.children = department.getTeam().stream()
                .map(t -> new TeamResDto(t))
                .collect(Collectors.toList());
    }
}
