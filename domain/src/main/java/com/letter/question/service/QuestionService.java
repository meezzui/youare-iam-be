package com.letter.question.service;

import com.letter.exception.CustomException;
import com.letter.exception.ErrorCode;
import com.letter.member.entity.Couple;
import com.letter.member.entity.Member;
import com.letter.member.repository.CoupleCustomRepositoryImpl;
import com.letter.member.repository.MemberRepository;
import com.letter.question.dto.*;
import com.letter.question.entity.Question;
import com.letter.question.entity.SelectQuestion;
import com.letter.question.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final MemberRepository memberRepository;
    private final CoupleCustomRepositoryImpl coupleCustomRepository;

    private final QuestionRepository questionRepository;
    private final QuestionCustomRepositoryImpl questionCustomRepository;
    private final SelectQuestionRepository selectQuestionRepository;
    private final AnswerCustomRepositoryImpl answerCustomRepository;

    public ResponseEntity<List<QuestionResponse.QuestionList>> getQuestionList() {
        // TODO 사용자 인증 추가
        memberRepository.findById("1").orElseThrow(
                () -> new RuntimeException(HttpStatus.UNAUTHORIZED.name())
        );

        // TODO 이미 선택한 질문 제외하고 응답

        return ResponseEntity.ok(questionCustomRepository.findAll());
    }

    public ResponseEntity<QuestionResponse.SelectedQuestion> selectOrRegisterQuestion(QuestionRequest.SelectOrRegisterQuestion selectOrRegisterQuestion) {
        Long selectedQuestion = null;

        memberRepository.findById("1").orElseThrow(
                () -> new RuntimeException(HttpStatus.UNAUTHORIZED.name())
        );

        // 질문 프리셋에 있는 질문 등록
        if (selectOrRegisterQuestion.getQuestionId() != null) {

            // 질문 프리셋에 없는 질문일 경우
            final Question question = questionRepository.findQuestionById(selectOrRegisterQuestion.getQuestionId()).orElseThrow(
                    () -> new CustomException(ErrorCode.QUESTION_NOT_FOUND)
            );

            final Couple couple = coupleCustomRepository.findCoupleInMemberByMemberId("1");
            if (couple == null) {
                throw new CustomException(ErrorCode.COUPLE_NOT_FOUND);
            }

            final int countSelectQuestion = selectQuestionRepository.countByQuestionAndCouple(question, couple);
            if (countSelectQuestion == 1) {
                throw new CustomException(ErrorCode.ALREADY_SELECTED_QUESTION);
            }

            // 정상적인 플로우, 질문 프리셋에서 질문을 고른 경우
            selectedQuestion = saveQuestion(question, couple);

        } else {
            // TODO 커스텀 질문 등록
            log.info("커스텀 질문 등록");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new QuestionResponse.SelectedQuestion(selectedQuestion));
    }

    public Long saveQuestion(Question question, Couple couple) {

        final SelectQuestion selectQuestion = SelectQuestion.builder()
                .question(question)
                .couple(couple)
                .build();
        selectQuestionRepository.save(selectQuestion);

        return selectQuestion.getId();
    }

    public ResponseEntity<LetterPaginationDto> getLetterList(int nextCursor) {
        final Member member = memberRepository.findById("1").orElseThrow(
                () -> new RuntimeException(HttpStatus.UNAUTHORIZED.name())
        );

        final Couple couple = coupleCustomRepository.findCoupleInMemberByMemberId("1");
        if (couple == null) {
            throw new CustomException(ErrorCode.COUPLE_NOT_FOUND);
        }

        final LetterPaginationDto letterPaginationDto = new LetterPaginationDto();

        final List<LetterDetailResponse> letterDetailResponses = questionCustomRepository.findAllByCoupleAndNextCursor(couple, nextCursor);
        if (letterDetailResponses.size() == 26) {
            final LetterDetailResponse nextLetter = letterDetailResponses.get(letterDetailResponses.size() - 1);
            letterPaginationDto.setNextCursor(Math.toIntExact(nextLetter.getSelectQuestionId()));
            letterDetailResponses.remove(25);
        }

        final List<DetailAnswerDto> databaseDetailAnswerDtoList = answerCustomRepository.findAllBySelectQuestionId(letterDetailResponses.get(0).getSelectQuestionId());

        final HashMap<Long, List<DetailAnswerDto>> letterDetailHashmap = new HashMap<>();

        categorizeAnswer(databaseDetailAnswerDtoList, letterDetailHashmap);
        getAnswerList(letterDetailResponses, letterDetailHashmap, member);

        letterPaginationDto.setLetters(letterDetailResponses);
        return ResponseEntity.ok(letterPaginationDto);
    }

    private void categorizeAnswer(
            List<DetailAnswerDto> databaseDetailAnswerDtoList, HashMap<Long,
            List<DetailAnswerDto>> letterDetailHashmap
    ) {
        for (DetailAnswerDto detailAnswerDto : databaseDetailAnswerDtoList) {
            if (!letterDetailHashmap.containsKey(detailAnswerDto.getSelectQuestionId())) {
                letterDetailHashmap.put(detailAnswerDto.getSelectQuestionId(), new ArrayList<>());
                letterDetailHashmap.get(detailAnswerDto.getSelectQuestionId()).add(detailAnswerDto);
            } else {
                letterDetailHashmap.get(detailAnswerDto.getSelectQuestionId()).add(detailAnswerDto);
            }
        }
    }

    private void getAnswerList(
            List<LetterDetailResponse> letterDetailResponses,
            HashMap<Long, List<DetailAnswerDto>> letterDetailHashmap,
            Member member
    ) {
        for (LetterDetailResponse letterDetailResponse : letterDetailResponses) {
            if (letterDetailHashmap.containsKey(letterDetailResponse.getSelectQuestionId()) && !letterDetailHashmap.get(letterDetailResponse.getSelectQuestionId()).isEmpty()) {
                final List<DetailAnswerDto> detailAnswerList = letterDetailHashmap.get(letterDetailResponse.getSelectQuestionId());
                letterDetailResponse.setAnswerCount(detailAnswerList.size());

                if (detailAnswerList.size() == 1 && Objects.equals(detailAnswerList.get(0).getMemberId(), member.getId())) {
                    letterDetailResponse.setMyAnswer(true);
                } else if (detailAnswerList.size() == 2) {
                    letterDetailResponse.setMyAnswer(true);
                    final DetailAnswerDto detailAnswerDto1 = detailAnswerList.get(0);
                    final DetailAnswerDto detailAnswerDto2 = detailAnswerList.get(1);

                    letterDetailResponse.setAnswer(new ArrayList<>());
                    letterDetailResponse.getAnswer().add(new AnswerResponse(detailAnswerDto1));
                    letterDetailResponse.getAnswer().add(new AnswerResponse(detailAnswerDto2));
                }
            }
        }
    }

}
