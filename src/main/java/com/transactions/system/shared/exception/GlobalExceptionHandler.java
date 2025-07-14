package com.transactions.system.shared.exception;

import com.transactions.system.shared.constants.ResponseCode;
import com.transactions.system.shared.dto.GenericResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public Mono<ResponseEntity<GenericResponseDto<Object>>> handleCustomException(CustomException ex) {
        log.error("Handled CustomException: {}", ex.getMessage());

        GenericResponseDto<Object> response = new GenericResponseDto<>(
                ex.getMessage(),
                ex.getFieldErrors()
        );

        return Mono.just(ResponseEntity
                .status(ex.getResponseCode().getHttpStatus())
                .body(response));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<GenericResponseDto<Object>>> handleGenericException(Exception ex) {
        log.error("Unhandled Exception: ", ex);

        GenericResponseDto<Object> response = new GenericResponseDto<>(
                ResponseCode.UNEXPECTED_ERROR.getMessage(),
                List.of()
        );

        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response));
    }
}
