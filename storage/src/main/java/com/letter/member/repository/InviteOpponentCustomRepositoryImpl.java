package com.letter.member.repository;

import com.letter.member.entity.InviteOpponent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
    private final EntityManager entityManager;

    public String findSelectedQuestionIdByLinkKey(String linkKey) {

        return jpaQueryFactory
                .select(question.questionContents)
                .from(inviteOpponent)
                .leftJoin(question).on(inviteOpponent.question.id.eq(question.id))
                .where(inviteOpponent.linkKey.eq(linkKey))
                .fetchOne();
    }

    public Long updateIsShow(String memberId){
        Long updateIsShow = jpaQueryFactory
                .update(inviteOpponent)
                .set(inviteOpponent.isShow, "N")
                .where(inviteOpponent.member.id.eq(memberId))
                .execute();

        entityManager.flush();
        entityManager.clear();

        return updateIsShow;
    }

}
