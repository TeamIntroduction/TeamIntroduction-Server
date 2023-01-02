package com.example.project.dto.team;

import com.example.project.domain.team.Team;
import com.example.project.domain.team.Type;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TeamResDto {

    private Long id;
    private String name;
    private Type type;
    private List<TeamResDto> children;

    public TeamResDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.type = team.getType();
        this.children = team.getChildren().stream()
                .map(c -> new TeamResDto(c))
                .collect(Collectors.toList());
    }
}
