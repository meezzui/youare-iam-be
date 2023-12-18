package com.letter.question;

import com.letter.question.dto.AnswerRequest;
import com.letter.question.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Answer Controller", description = "답변 관련 컨트롤러")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @Operation(summary = "답변 등록", description = "Request body로 선택 질문 id와 그 질문의 답변을 받아 등록하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "등록 성공"
                    )
            }
    )
    @PostMapping("/answer")
    public ResponseEntity<?> registerAnswer(@RequestBody AnswerRequest answerRequest) {
        return answerService.registerAnswer(answerRequest);
    }
}
