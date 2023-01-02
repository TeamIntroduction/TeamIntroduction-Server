package com.example.project.service;

import com.example.project.dto.part.PartResDto;
import com.example.project.dto.team.TeamResDto;
import com.example.project.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public List<TeamResDto> getTeams() {

        return teamRepository.findTopTeam().stream()
                .map(t -> new TeamResDto(t))
                .collect(Collectors.toList());
    }

    public List<PartResDto> getParts() {

        return teamRepository.findParts().stream()
                .map(t -> new PartResDto(t))
                .collect(Collectors.toList());
    }
}
