package com.letter.member.repository;

import com.letter.member.entity.InviteOpponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InviteOpponentRepository extends JpaRepository<InviteOpponent,Long> {

}
