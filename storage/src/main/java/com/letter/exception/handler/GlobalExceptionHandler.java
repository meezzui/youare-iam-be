package com.letter.exception.handler;

import com.letter.exception.CustomException;
import com.letter.exception.ErrorCode;
import com.letter.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<ExceptionResponse> handlerCustomException(CustomException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(new ExceptionResponse(errorCode.getStatus().value(), errorCode.getMessage()));
    }

}
