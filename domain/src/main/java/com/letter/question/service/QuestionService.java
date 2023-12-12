package com.letter.question.service;

import com.letter.member.entity.Couple;
import com.letter.member.repository.CoupleCustomRepositoryImpl;
import com.letter.member.repository.MemberRepository;
import com.letter.question.dto.QuestionRequest;
import com.letter.question.dto.QuestionResponse;
import com.letter.question.entity.Question;
import com.letter.question.entity.SelectQuestion;
import com.letter.question.repository.QuestionCustomRepositoryImpl;
import com.letter.question.repository.QuestionRepository;
import com.letter.question.repository.SelectQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final MemberRepository memberRepository;
    private final CoupleCustomRepositoryImpl coupleCustomRepository;

    private final QuestionRepository questionRepository;
    private final QuestionCustomRepositoryImpl questionCustomRepository;
    private final SelectQuestionRepository selectQuestionRepository;

    public ResponseEntity<List<QuestionResponse.QuestionList>> getQuestionList() {
        // TODO 사용자 인증 추가

        return ResponseEntity.ok(questionCustomRepository.findAll());
    }

    // TODO servletServerHttpResponse 여러개인 것 수정
    public void selectOrRegisterQuestion(QuestionRequest.SelectOrRegisterQuestion selectOrRegisterQuestion) {

        // 질문 프리셋에 있는 질문 등록
        if (selectOrRegisterQuestion.getQuestionId() != null){

            // 질문 프리셋에 없는 질문일 경우
            final Question question = questionRepository.findQuestionById(selectOrRegisterQuestion.getQuestionId())
                    .orElseThrow(() -> new RuntimeException(HttpStatus.BAD_REQUEST.name()));

            // TODO 이미 고른 질문일 경우
            final Couple couple = coupleCustomRepository.findCoupleInMemberByMemberId("1");

            final int countSelectQuestion = selectQuestionRepository.countByQuestionAndCouple(question, couple);
            if (countSelectQuestion == 1) {
                throw new RuntimeException(HttpStatus.BAD_REQUEST.name());
            }


            // 정상적인 플로우, 질문 프리셋에서 질문을 고른 경우
            saveQuestion(question, couple);


        } else {
            // TODO 커스텀 질문 등록
            log.info("커스텀 질문 등록");
        }

    }

    public void saveQuestion(Question question, Couple couple) {


        final SelectQuestion selectQuestion = SelectQuestion.builder()
                .question(question)
                .couple(couple)
                .build();
        selectQuestionRepository.save(selectQuestion);

    }

}
