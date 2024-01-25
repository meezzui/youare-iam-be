package com.letter.member.dto;

import com.letter.member.dto.role.UserStatusRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberStatusResponse {

    private final UserStatusRole userStatus;
    private final String linkKey;
}
