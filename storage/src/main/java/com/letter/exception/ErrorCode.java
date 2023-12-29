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
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"찾는 회원 아이디가 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"찾으시는 결과가 없습니다."),

    /* 409 */
    ALREADY_SELECTED_QUESTION(HttpStatus.CONFLICT, "이미 선택했던 질문입니다."),
    ALREADY_ANSWER(HttpStatus.CONFLICT, "질문에 이미 답변을 작성했습니다."),
    ALREADY_COUPLE(HttpStatus.CONFLICT,"이미 커플이 된 회원입니다."),

    /* 401 */
    UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED,"잘못된 JWT 토큰 입니다. 토큰이 비어있을 수 있으니 확인해주세요."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"만료된 JWT token 입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"유효하지 않는 JWT 서명 입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED,"지원되지 않는 JWT 토큰 입니다."),

    /* 400 */
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"잘못된 요청값 입니다. 다시 확인해주새요."),
    MEMBER_BAD_REQUEST(HttpStatus.BAD_REQUEST,"초대한 사람의 아이디와 초대된 사람의 아이디가 같습니다.");

    private final HttpStatus status;
    private final String message;

}
