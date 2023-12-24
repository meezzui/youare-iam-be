package com.letter.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class OAuthResponse {
    private String nickname;
    private String email;
    private Long id;

}
