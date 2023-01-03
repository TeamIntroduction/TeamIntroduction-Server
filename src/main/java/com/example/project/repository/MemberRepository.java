package com.example.project.repository;

import com.example.project.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.team.id=:teamId")
    List<Member> findByTeamId(@Param("teamId") Long teamId);

    @EntityGraph(attributePaths = {"team"})
    Optional<Member> findById(@Param("memberId") Long memberId);
}
