package com.example.project.dto.team;

import com.example.project.domain.team.Team;
import com.example.project.domain.team.TeamName;
import com.example.project.dto.part.PartResDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TeamResDto {

    private Long id;
    private TeamName name;
    private List<PartResDto> children;

    public TeamResDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.children = team.getPart().stream()
                .map(p -> new PartResDto(p))
                .collect(Collectors.toList());
    }
}
