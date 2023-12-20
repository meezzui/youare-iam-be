package com.letter.member.dto;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class MemberResponse {

    @RequiredArgsConstructor
    @Builder
    @AllArgsConstructor
    @Data
    @ApiResponses(value ={
        @ApiResponse(description = "상대 초대 링크 생성 response")
    })
    public static class CreateInviteLinkResponse{
        private String linkKey;
        private String question;
    }

    @RequiredArgsConstructor
    @Builder
    @AllArgsConstructor
    @Data
    @ApiResponses(value ={
            @ApiResponse(description = "초대 수락 response")
    })
    public static class AcceptInviteLinkResponse{
        private Long selectedQuestionId;
    }

    @RequiredArgsConstructor
    @Builder
    @AllArgsConstructor
    @Data
    @ApiResponses(value ={
            @ApiResponse(description = "초대 링크로 랜딩되는 페이지 관련 response")
    })
    public static class InvitedPersonInfoResponse{
        private Long selectedQuestionId;
        private String invitedPersonName;
    }

}
