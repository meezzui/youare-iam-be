package com.letter.question;

import com.letter.annotation.LoginCheck;
import com.letter.annotation.User;
import com.letter.member.entity.Member;
import com.letter.question.dto.AnswerRequest;
import com.letter.question.dto.QuestionContentsResponse;
import com.letter.question.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Answer Controller", description = "답변 관련 컨트롤러")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @Operation(summary = "답변 등록 렌더링 관련 데이터 조회", description = "query string으로 selectedQuestionId를 받아서 question 응답하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공"
                    )
            }
    )
    @LoginCheck
    @GetMapping("/answer")
    public ResponseEntity<QuestionContentsResponse> getAnswersQuestion(
            @RequestParam(name = "selected-question-id") Long selectedQuestionId,
            @User Member member) {
        return answerService.getAnswersQuestion(selectedQuestionId, member);
    }

    @Operation(summary = "답변 등록", description = "Request body로 선택 질문 id와 그 질문의 답변을 받아 등록하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "등록 성공"
                    )
            }
    )
    @LoginCheck
    @PostMapping("/answer")
    public ResponseEntity<?> registerAnswer(
            @RequestBody AnswerRequest answerRequest,
            @User Member member) {
        return answerService.registerAnswer(answerRequest, member);
    }
}
