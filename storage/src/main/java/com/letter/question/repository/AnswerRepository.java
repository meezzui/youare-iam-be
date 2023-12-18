package com.letter.question.repository;

import com.letter.member.entity.Member;
import com.letter.question.entity.Answer;
import com.letter.question.entity.SelectQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    int countByMemberAndSelectQuestion(Member member, SelectQuestion selectQuestion);
}
