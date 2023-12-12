package com.letter.question.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class QuestionResponse {

    @Getter
    @Setter
    public static class QuestionList {
        private Long id;

        @NotNull
        @Size(max = 200)
        private String question;

    }

}

