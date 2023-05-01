package com._8attery.seesaw.dto.exception.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorResponseDto {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    @Builder(access = AccessLevel.PRIVATE)
    private ErrorResponseDto(final int status, final String error, final String message,
                             final String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public static ErrorResponseDto of(final HttpStatus httpStatus, final String message,
                                      final HttpServletRequest httpServletRequest) {
        return ErrorResponseDto.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .path(httpServletRequest.getRequestURI())
                .build();
    }

}