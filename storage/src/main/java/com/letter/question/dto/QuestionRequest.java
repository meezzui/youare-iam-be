package com.letter.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class QuestionRequest {

    private QuestionRequest() {}

    @Getter
    @NoArgsConstructor
    public static class SelectOrRegisterQuestion {
        private Long questionId;

        private String question;
    }

}
