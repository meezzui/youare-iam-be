package com.letter.member.repository;

import com.letter.member.entity.Couple;
import static com.letter.member.entity.QCouple.couple;

import static com.letter.member.entity.QMember.member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoupleCustomRepositoryImpl implements CoupleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Couple findCoupleInMemberByMemberId(String memberId) {
        return jpaQueryFactory
                .select(couple)
                .from(member)
                .join(couple)
                .on(member.couple.eq(couple))
                .where(member.id.eq(memberId),
                        couple.isShow.eq("Y"))
                .fetchOne();
    }

}
