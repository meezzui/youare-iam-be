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
import org.springframework.web.bind.annotation.*;

@Tag(name = "OAuth", description = "상대 초대 관련 API")
@RestController
@RequestMapping("/api/v1/members/invite")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;



    @Operation(summary = "상대 초대 링크 API")
    @ApiResponses(value ={
            @ApiResponse(responseCode= "200",description = "토큰 발급 성공")
    })
    @PostMapping("/link")
    public ResponseEntity<MemberResponse.CreateInviteLinkResponse> createInviteLink(@RequestBody @Valid MemberRequest.CreateInviteLinkRequest request){

        // TODO: 회원 인증
        return ResponseEntity.ok().body(memberService.createInviteLink(request));
    }

}
