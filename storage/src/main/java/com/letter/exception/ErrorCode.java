package com.letter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 404 */
    COUPLE_NOT_FOUND(HttpStatus.NOT_FOUND, "커플로 등록되지 않은 사용자입니다."),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "프리셋에 존재하지 않는 질문입니다."),
    SELECT_QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "선택 질문을 찾을 수 없습니다."),

    /* 409 */
    ALREADY_SELECTED_QUESTION(HttpStatus.CONFLICT, "이미 선택했던 질문입니다."),
    ALREADY_ANSWER(HttpStatus.CONFLICT, "질문에 이미 답변을 작성했습니다.");

    private final HttpStatus status;
    private final String message;

}
