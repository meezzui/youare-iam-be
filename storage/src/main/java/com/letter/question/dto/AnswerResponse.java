package com.letter.question.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AnswerResponse {
    @NotNull
    private final String memberId;

    @NotNull
    private final String memberName;

    @NotNull
    private final String answer;

    @NotNull
    private final LocalDateTime createdAt;

    public AnswerResponse(DetailAnswerDto detailAnswerDto) {
        this.memberId = detailAnswerDto.getMemberId();
        this.memberName = detailAnswerDto.getMemberName();
        this.answer = detailAnswerDto.getAnswer();
        this.createdAt = detailAnswerDto.getCreatedAt();
    }
}
