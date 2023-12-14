package com.letter.member.dto;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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


}
