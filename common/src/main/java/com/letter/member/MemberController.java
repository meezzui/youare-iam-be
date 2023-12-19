package com.letter.member;

import com.letter.annotation.LoginCheck;
import com.letter.member.dto.MemberRequest;
import com.letter.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @LoginCheck
    @PostMapping("/link")
    public ResponseEntity<MemberResponse.CreateInviteLinkResponse> createInviteLink(@RequestBody @Valid MemberRequest.CreateInviteLinkRequest request){
        // TODO: 사용자 인증

        return ResponseEntity.ok().body(memberService.createInviteLink(request));
    }

    @Operation(summary = "초대 수락 API")
    @ApiResponses(value ={
            @ApiResponse(responseCode= "201",description = "초대 수락 완료")
    })
    @LoginCheck
    @PostMapping("/accept")
    public ResponseEntity<MemberResponse.AcceptInviteLinkResponse> acceptedInvite(@RequestBody @Valid MemberRequest.AcceptInviteLinkRequest request){
        // TODO: 사용자 인증

        return ResponseEntity.ok().body(memberService.acceptedInvite(request));
    }

    @Operation(summary = "초대 링크로 랜딩되는 페이지 API")
    @ApiResponses(value ={
            @ApiResponse(responseCode= "200",description = "정보 가져오기 완료")
    })
    @LoginCheck
    @GetMapping ("/info")
    public ResponseEntity<MemberResponse.InvitedPersonInfoResponse> getInvitedPersonInfo(@Valid MemberRequest.InvitedPersonInfoRequest request){
        // TODO: 사용자 인증

        return ResponseEntity.ok().body(memberService.getInvitedPersonInfo(request));
    }
}
