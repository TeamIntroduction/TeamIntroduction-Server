package com.example.project.controller;

import com.example.project.dto.ResponseDto;
import com.example.project.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.project.constant.SuccessResponse.GET_TEAMS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping()
    public ResponseEntity<ResponseDto> getTeams() {

        return new ResponseEntity<>(ResponseDto.success(GET_TEAMS, teamService.getTeams()), HttpStatus.OK);
    }
}
