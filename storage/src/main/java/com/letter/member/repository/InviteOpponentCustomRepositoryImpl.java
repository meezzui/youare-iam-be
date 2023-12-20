package com.letter.member.repository;

import com.letter.member.entity.InviteOpponent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.letter.member.entity.QCouple.couple;
import static com.letter.member.entity.QMember.member;
import static com.letter.question.entity.QSelectQuestion.selectQuestion;
import static com.letter.member.entity.QInviteOpponent.inviteOpponent;
import static com.letter.question.entity.QQuestion.question;

@Repository
@RequiredArgsConstructor
public class InviteOpponentCustomRepositoryImpl implements InviteOpponentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Long findSelectedQuestionIdByLinkKey(String linkKey) {

        return jpaQueryFactory
                .select(selectQuestion.id)
                .from(inviteOpponent)
                .leftJoin(question).on(inviteOpponent.question.id.eq(question.id))
                .leftJoin(selectQuestion).on(question.id.eq(selectQuestion.question.id))
                .where(inviteOpponent.linkKey.eq(linkKey))
                .fetchOne();
    }

}
