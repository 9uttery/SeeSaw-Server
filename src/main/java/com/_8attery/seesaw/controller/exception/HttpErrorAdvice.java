package com._8attery.seesaw.controller.exception;

import com._8attery.seesaw.dto.exception.response.ErrorResponseDto;
import com._8attery.seesaw.exception.InvalidRequestException;
import com._8attery.seesaw.exception.ResourceNotFoundException;
import com._8attery.seesaw.exception.UserUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class HttpErrorAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handlerException(final IllegalArgumentException e, final HttpServletRequest request) {
        return ResponseEntity.badRequest()
                .body(ErrorResponseDto.of(HttpStatus.BAD_REQUEST, e.getMessage(), request));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerException(final ResourceNotFoundException e, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.of(HttpStatus.NOT_FOUND, e.getMessage(), request));
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handlerException(final UserUnauthorizedException e, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDto.of(HttpStatus.UNAUTHORIZED, e.getMessage(), request));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDto> handlerException(final NullPointerException e, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponseDto> handlerException(final InvalidRequestException e, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDto.of(HttpStatus.CONFLICT, e.getMessage(), request));
    }

}
