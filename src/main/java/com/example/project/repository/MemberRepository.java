package com.example.project.repository;

import com.example.project.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"part"})
    @Query("SELECT m FROM Member m WHERE m.part.id=:partId")
    List<Member> findByPartId(@Param("partId") Long partId);

    @EntityGraph(attributePaths = {"part"})
    Optional<Member> findById(@Param("memberId") Long memberId);
}
