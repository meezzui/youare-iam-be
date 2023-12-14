package com.letter.member.repository;

import com.letter.member.entity.InviteOpponent;
import com.letter.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteOpponentRepository extends JpaRepository<InviteOpponent,Long> {

}
