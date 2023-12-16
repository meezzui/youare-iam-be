package com.letter.question;

import com.letter.question.dto.*;
import com.letter.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Question Controller", description = "질문 관련 컨트롤러")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionResponse.QuestionList>> getQuestionList() {
        return questionService.getQuestionList();
    }

    @Operation(summary = "질문 선택 or 등록", description = "Request body로 질문의 id(프리셋)를 받아서 등록하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "등록 성공",
                            content = @Content(schema = @Schema(implementation = QuestionResponse.SelectedQuestion.class))
                    )
            }
    )
    @PostMapping("/questions")
    public ResponseEntity<QuestionResponse.SelectedQuestion> selectOrRegisterQuestion(@RequestBody QuestionRequest.SelectOrRegisterQuestion selectOrRegisterQuestion) {
        return questionService.selectOrRegisterQuestion(selectOrRegisterQuestion);
    }

    @Operation(summary = "대화 상세 페이지 조회", description = "주고 받은 질문과 답변 조회 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = LetterPaginationDto.class))
                    )
            }
    )
    @GetMapping("/letters")
    public ResponseEntity<LetterPaginationDto> getLetterList(
            @RequestParam(
                    name = "next-cursor",
                    required = false,
                    defaultValue = "1"
            ) int nextCursor) {
        return questionService.getLetterList(nextCursor);
    }
}
