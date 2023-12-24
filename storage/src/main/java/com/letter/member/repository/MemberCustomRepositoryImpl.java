package com.letter.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.letter.member.entity.QMember.member;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public String findIdByKakaId(Long id) {
        try{
            return jpaQueryFactory
                    .select(member.id)
                    .from(member)
                    .where(member.kakaoId.eq(id))
                    .fetchOne();
        }
        catch (QueryException q){
            log.info("쿼리 실패");
            return null;
        }
    }
}
