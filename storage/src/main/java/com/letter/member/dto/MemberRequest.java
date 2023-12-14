package com.letter.member.dto;

import com.letter.member.entity.InviteOpponent;
import com.letter.member.entity.Member;
import com.letter.question.entity.Question;
import lombok.*;


@Data
public class MemberRequest {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CreateInviteLinkRequest{

        private Long questionId;
        private String answer;

        public InviteOpponent toCreateInviteLink(String uuid, Question question, Member member) {
            return InviteOpponent.builder()
                    .question(question)
                    .member(member)
                    .answer(answer)
                    .linkKey(uuid)
                    .build();
        }
    }


}
