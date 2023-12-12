package com.letter.question.repository;

import com.letter.question.dto.QuestionResponse;
import static com.letter.question.entity.QQuestion.question;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionCustomRepositoryImpl implements QuestionCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 질문 리스트 조회
    public List<QuestionResponse.QuestionList> findAll() {
        return jpaQueryFactory
                .select(Projections.bean(QuestionResponse.QuestionList.class,
                        question.id,
                        question.questionContents.as("question")))
                .from(question)
                .fetch();
    }
}
