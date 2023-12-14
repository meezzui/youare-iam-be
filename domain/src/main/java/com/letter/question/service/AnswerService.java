package com.letter.question.service;

import com.letter.member.entity.Member;
import com.letter.member.repository.MemberRepository;
import com.letter.question.dto.AnswerRequest;
import com.letter.question.entity.Answer;
import com.letter.question.entity.SelectQuestion;
import com.letter.question.repository.AnswerRepository;
import com.letter.question.repository.SelectQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final MemberRepository memberRepository;

    private final AnswerRepository answerRepository;
    private final SelectQuestionRepository selectQuestionRepository;

    public ResponseEntity<?> registerAnswer(AnswerRequest answerRequest) {
        final Member member = memberRepository.findById("1").orElseThrow(
                () -> new RuntimeException(HttpStatus.UNAUTHORIZED.name())
        );

        final SelectQuestion selectQuestion = selectQuestionRepository.findById(answerRequest.getSelectQuestionId()).orElseThrow(
                () -> new RuntimeException(HttpStatus.BAD_REQUEST.name())
        );

        final int count = answerRepository.countByMemberAndSelectQuestion(member, selectQuestion);

        if (count == 1) {
            throw new RuntimeException(HttpStatus.BAD_REQUEST.name());
        }

        answerRepository.save(new Answer(member, selectQuestion, answerRequest.getAnswer()));

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}
