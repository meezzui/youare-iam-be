package com.letter.question.repository;

import com.letter.member.entity.Couple;
import com.letter.question.dto.LetterDetailResponse;
import com.letter.question.dto.QuestionResponse;

import static com.letter.question.entity.QQuestion.question;
import static com.letter.question.entity.QSelectQuestion.selectQuestion;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionCustomRepositoryImpl implements QuestionCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 질문 리스트 조회
    public List<QuestionResponse.QuestionList> findAll() {
        return jpaQueryFactory
                .select(Projections.bean(QuestionResponse.QuestionList.class,
                        question.id.as("questionId"),
                        question.questionContents.as("question")))
                .from(question)
                .where(question.isShow.eq("Y"))
                .fetch();
    }

    public List<LetterDetailResponse> findAllByCoupleAndNextCursor(Couple couple, int nextCursor) {
        return jpaQueryFactory
                .select(Projections.bean(LetterDetailResponse.class,
                        selectQuestion.id.as("selectQuestionId"),
                        question.questionContents.as("question"),
                        selectQuestion.createdAt))
                .from(selectQuestion)
                .leftJoin(question)
                .on(selectQuestion.question.id.eq(question.id),
                        question.isShow.eq("Y"))
                .where(selectQuestion.id.loe(nextCursor), selectQuestion.couple.eq(couple), selectQuestion.isShow.eq("Y"))
                .orderBy(selectQuestion.id.desc())
                .limit(26)
                .fetch();
    }

}