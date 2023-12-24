package com.letter.member.dto;

import com.letter.member.entity.Couple;
import com.letter.member.entity.InviteOpponent;
import com.letter.member.entity.Member;
import com.letter.question.entity.Answer;
import com.letter.question.entity.Question;
import com.letter.question.entity.SelectQuestion;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.*;

@Data
public class MemberRequest {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CreateInviteLinkRequest{ // 초대 링크 생성 관련 request

        private Long questionId;
        private String answer;

        public InviteOpponent toCreateInviteLink(String uuid, Question question, Member member) {
            return InviteOpponent.builder()
                    .question(question)
                    .member(member)
                    .answer(answer)
                    .linkKey(uuid)
                    .isShow("Y")
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class AcceptInviteLinkRequest{ // 초대 수락 관련 request

        private String linkKey;
        private String answer;

        public Couple toCoupleInfo() {
            return Couple.builder()
                    .isShow("Y")
                    .build();
        }

        public SelectQuestion toSelectQuestion(Couple couple,Question question) {
            return SelectQuestion.builder()
                    .question(question)
                    .couple(couple)
                    .isShow("Y")
                    .build();
        }

        public Answer toAnswerInfo(Member member, SelectQuestion selectQuestion, String answer) {
            return Answer.builder()
                    .member(member)
                    .couple(selectQuestion.getCouple())
                    .selectQuestion(selectQuestion)
                    .answerContents(answer)
                    .isShow("Y")
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class InvitedPersonInfoRequest{ // 초대된 링크로 랜딩되는 페이지 관련 request

        private String linkKey;

    }


}
