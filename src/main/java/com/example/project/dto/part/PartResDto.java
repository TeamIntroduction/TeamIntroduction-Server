package com.example.project.dto.part;

import com.example.project.domain.team.Team;
import lombok.Data;

@Data
public class PartResDto {
    private Long id;
    private String name;

    public PartResDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}
