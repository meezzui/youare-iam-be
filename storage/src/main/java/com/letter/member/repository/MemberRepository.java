package com.letter.member.repository;

import com.letter.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {

    // 총 회원 수 카운트
    long countAllBy();
}
