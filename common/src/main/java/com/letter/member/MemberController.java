package com.letter.member;

import com.letter.member.dto.MemberRequest;
import com.letter.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Invite", description = "초대 관련 API")
@RestController
@RequestMapping("/api/v1/members/invite")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "초대 링크 생성 API")
    @ApiResponses(value ={
            @ApiResponse(responseCode= "201",description = "초대 링크 키 생성 완료")
    })
    @PostMapping("/link")
    public ResponseEntity<MemberResponse.CreateInviteLinkResponse> createInviteLink(@RequestBody @Valid MemberRequest.CreateInviteLinkRequest request){
        // TODO: 사용자 인증

        return ResponseEntity.ok().body(memberService.createInviteLink(request));
    }

    @Operation(summary = "초대 수락 API")
    @ApiResponses(value ={
            @ApiResponse(responseCode= "201",description = "초대 수락 완료")
    })
    @PostMapping("/accept")
    public ResponseEntity<MemberResponse.AcceptInviteLinkResponse> acceptedInvite(@RequestBody @Valid MemberRequest.AcceptInviteLinkRequest request){
        // TODO: 사용자 인증

        return ResponseEntity.ok().body(memberService.acceptedInvite(request));
    }


}
