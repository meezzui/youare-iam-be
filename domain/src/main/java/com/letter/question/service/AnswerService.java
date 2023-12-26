package com.letter.question.service;

import com.letter.exception.CustomException;
import com.letter.exception.ErrorCode;
import com.letter.member.entity.Couple;
import com.letter.member.entity.Member;
import com.letter.member.repository.CoupleCustomRepositoryImpl;
import com.letter.question.dto.AnswerRequest;
import com.letter.question.dto.QuestionContentsResponse;
import com.letter.question.entity.Answer;
import com.letter.question.entity.SelectQuestion;
import com.letter.question.repository.AnswerRepository;
import com.letter.question.repository.QuestionCustomRepositoryImpl;
import com.letter.question.repository.SelectQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final CoupleCustomRepositoryImpl coupleCustomRepository;

    private final AnswerRepository answerRepository;
    private final SelectQuestionRepository selectQuestionRepository;
    private final QuestionCustomRepositoryImpl questionCustomRepository;

    public ResponseEntity<QuestionContentsResponse> getAnswersQuestion(Long selectedQuestionId, Member member) {

        final Couple couple = coupleCustomRepository.findCoupleInMemberByMemberId(member.getId());
        if (couple == null) {
            throw new CustomException(ErrorCode.COUPLE_NOT_FOUND);
        }

        final QuestionContentsResponse questionContentsResponse = questionCustomRepository.findQuestionContentsBySelectQuestionIdAndCouple(selectedQuestionId, couple);

        if (questionContentsResponse == null) {
            throw new CustomException(ErrorCode.SELECT_QUESTION_NOT_FOUND);
        }

        return ResponseEntity.ok(questionContentsResponse);
    }

    public ResponseEntity<?> registerAnswer(AnswerRequest answerRequest, Member member) {

        final Couple couple = coupleCustomRepository.findCoupleInMemberByMemberId(member.getId());
        if (couple == null) {
            throw new CustomException(ErrorCode.COUPLE_NOT_FOUND);
        }

        final SelectQuestion selectQuestion = selectQuestionRepository.findByIdAndCouple(answerRequest.getSelectQuestionId(), couple).orElseThrow(
                () -> new CustomException(ErrorCode.SELECT_QUESTION_NOT_FOUND)
        );

        final int count = answerRepository.countByMemberAndSelectQuestion(member, selectQuestion);

        if (count == 1) {
            throw new CustomException(ErrorCode.ALREADY_ANSWER);
        }

        answerRepository.save(new Answer(couple, member, selectQuestion, answerRequest.getAnswer()));

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}
