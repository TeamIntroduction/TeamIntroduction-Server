package com.example.project.repository;

import com.example.project.domain.member.Member;
import com.example.project.domain.team.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @EntityGraph(attributePaths = {"children"})
    @Query("SELECT t FROM Team t WHERE t.parent.id is null")
    List<Team> findTopTeam();
}
