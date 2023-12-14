package com.letter.member;

import com.letter.member.dto.MemberRequest;
import com.letter.member.dto.MemberResponse;
import com.letter.member.entity.InviteOpponent;
import com.letter.member.entity.Member;
import com.letter.member.repository.InviteOpponentRepository;
import com.letter.member.repository.MemberRepository;
import com.letter.question.entity.Question;
import com.letter.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final InviteOpponentRepository inviteOpponentRepository;

    public MemberResponse.CreateInviteLinkResponse createInviteLink(MemberRequest.CreateInviteLinkRequest request) {
        // 링크 고유 키 생성
        String uuid = UUID.randomUUID().toString();

        // 질문 아이디 조회
        Question question = questionRepository.findQuestionById(request.getQuestionId()).orElseThrow(
                () -> new RuntimeException(HttpStatus.BAD_REQUEST.name()));

        // TODO: 토큰에서 회원 아이디 가져와서 셋팅하기
        // 회원 아이디 조회
        Member member = memberRepository.findById("2023121200002").orElseThrow(
                () -> new RuntimeException(HttpStatus.UNAUTHORIZED.name())
        );

        // 상대 초대 테이블에 정보 request 셋팅
        InviteOpponent inviteOpponent = request.toCreateInviteLink(uuid,question,member);

        // 상대 초대 테이블에 정보 등록
        inviteOpponentRepository.save(inviteOpponent);

        return MemberResponse.CreateInviteLinkResponse.builder()
                .linkKey(uuid)
                .question(question.getQuestionContents())
                .build();

    }
}
