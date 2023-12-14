package com.letter.member.dto;

import lombok.*;

@RequiredArgsConstructor
@Data
public class MemberResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CreateInviteLinkResponse{
        private String linkKey;
        private String question;
    }


}
