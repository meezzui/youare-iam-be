package com.letter.member.dto;

import com.letter.member.entity.InviteOpponent;
import com.letter.member.entity.Member;
import com.letter.question.entity.Question;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class MemberRequest {

    @RequiredArgsConstructor
    @Builder
    @AllArgsConstructor
    @Data
    @ApiResponses(value ={
        @ApiResponse(description = "상대 초대 링크 생성 request")
    })
    public static class ReqCreateInviteLink{
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
