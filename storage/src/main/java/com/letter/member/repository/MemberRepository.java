package com.letter.member.repository;

import com.letter.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {

    // 이메일과 미디어 구분 값으로 디비에 있는 회원 정보 조회
    Optional<Member> findByAndEmailAndMediaSeparator(String email, String media);

    // 총 회원 수 카운트
    long countAllBy();
}
