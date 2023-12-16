package com.letter.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "DB에서 답변 응답에 필요한 데이터를 담는 DTO")
@Getter
@Setter
public class DetailAnswerDto {
    @Schema(description = "대화방 질문 ID")
    @NotNull
    private Long selectQuestionId;

    @Schema(description = "답변 작성자 ID")
    @NotNull
    private String memberId;

    @Schema(description = "답변 작성자 이름")
    @NotNull
    private String memberName;

    @Schema(description = "답변 내용")
    @NotNull
    private String answer;

    @Schema(description = "답변 생성 날")
    @NotNull
    private LocalDateTime createdAt;

}
