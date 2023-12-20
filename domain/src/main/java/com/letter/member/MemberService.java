package com.letter.member;

import com.letter.exception.CustomException;
import com.letter.exception.ErrorCode;
import com.letter.jwt.JwtProvider;
import com.letter.member.dto.MemberRequest;
import com.letter.member.dto.MemberResponse;
import com.letter.member.entity.Couple;
import com.letter.member.entity.InviteOpponent;
import com.letter.member.entity.Member;
import com.letter.member.repository.*;
import com.letter.question.entity.Answer;
import com.letter.question.entity.Question;
import com.letter.question.entity.SelectQuestion;
import com.letter.question.repository.AnswerRepository;
import com.letter.question.repository.QuestionCustomRepositoryImpl;
import com.letter.question.repository.QuestionRepository;
import com.letter.question.repository.SelectQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final InviteOpponentRepository inviteOpponentRepository;
    private final AnswerRepository answerRepository;
    private final SelectQuestionRepository selectQuestionRepository;

    private final JwtProvider jwtProvider;
    private final InviteOpponentCustomRepositoryImpl inviteOpponentCustomRepository;

    /**
     * 상대 초대 링크 생성 api
     *
     * @param request
     * @return
     */
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
        InviteOpponent inviteOpponent = request.toCreateInviteLink(uuid, question, member);

        // 상대 초대 테이블에 정보 등록
        inviteOpponentRepository.save(inviteOpponent);

        return MemberResponse.CreateInviteLinkResponse.builder()
                .linkKey(uuid)
                .question(question.getQuestionContents())
                .build();
    }


    /**
     * 초대 수락 API
     *
     * @param request
     * @return
     */
    public MemberResponse.AcceptInviteLinkResponse acceptedInvite(MemberRequest.AcceptInviteLinkRequest request) {


        // TODO: 토큰에서 회원 아이디 가져와서 셋팅하기
        // 회원 아이디 조회
        Member member = memberRepository.findById("2023121200002").orElseThrow(
                () -> new RuntimeException(HttpStatus.UNAUTHORIZED.name())
        );

        // 커플 정보 request 셋팅
        // 선택 질문 테이블 등록될 때 자동으로 등록 됨
        Couple couple = request.toCoupleInfo();

        // 링크 고유 값으로 상대 초대 테이블에 질문 아이디 조회
        InviteOpponent inviteOpponent = inviteOpponentRepository.findQuestionByLinkKey(request.getLinkKey()).orElseThrow(
                () -> new RuntimeException(HttpStatus.BAD_REQUEST.name())
        );

        if (Objects.isNull(inviteOpponent.getQuestion())) {
            log.error("질문 아이디가 null 입니다.");
        }

        // 선택 질문 정보 request 셋팅
        SelectQuestion selectQuestion = request.toSelectQuestion(couple, inviteOpponent.getQuestion());

        // 선택 질문 테이블에 정보 등록
        selectQuestionRepository.save(selectQuestion);

        // 답변 테이블에 정보 request 셋팅
        Answer answer = request.toAnswerInfo(member, selectQuestion, request.getAnswer());

        // 답변 테이블에 정보 등록
        answerRepository.save(answer);

        return MemberResponse.AcceptInviteLinkResponse.builder()
                .selectedQuestionId(selectQuestion.getId())
                .build();
    }

    /**
     * 초대 링크로 랜딩되는 페이지 관련 API
     *
     * @param linkKey
     * @return
     */
    public MemberResponse.InvitedPersonInfoResponse getInvitedPersonInfo(String linkKey) {

        // 상대 테이블에서 링크 고유 값으로 회원 조회
        InviteOpponent inviteOpponent = inviteOpponentRepository.findQuestionByLinkKey(linkKey).orElseThrow(
                () -> new CustomException(ErrorCode.BAD_REQUEST)
        );

        // 회원 이름
        String name = inviteOpponent.getMember().getName();

        // 선택된 질문 아이디
        Long selectedQuestionId = inviteOpponentCustomRepository.findSelectedQuestionIdByLinkKey(linkKey);

        return MemberResponse.InvitedPersonInfoResponse.builder()
                .invitedPersonName(name)
                .selectedQuestionId(selectedQuestionId)
                .build();
    }
}
