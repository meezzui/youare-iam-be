package com.letter.member.repository;

import com.letter.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.letter.member.entity.QMember.member;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Member> findIdByKakaoId(Long kakaoId) {
        try{
            return Optional.ofNullable(jpaQueryFactory
                    .select(member)
                    .from(member)
                    .where(member.kakaoId.eq(kakaoId))
                    .fetchOne());
        }
        catch (QueryException q){
            log.error("쿼리 실패");
            return null;
        }
    }

}
