package com.zeroboase.reservation.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR.value(), "내부 서버 오류 발생"),
    TOKEN_EXPIRED(UNAUTHORIZED.value(), "토큰이 만료됐습니다."),
    JWT_ERROR(UNAUTHORIZED.value(), "JWT 예외가 발생했습니다."),
    ACCESS_DENIED(FORBIDDEN.value(), "접근 권한이 없습니다."),
    VALIDATION_FAIL(BAD_REQUEST.value(), "유효성 검사 실패"),
    USERNAME_NOT_FOUND(UNAUTHORIZED.value(), "존재하지 않는 사용자입니다."),
    AUTHENTICATE_FAIL(UNAUTHORIZED.value(), "사용자 인증을 실패했습니다."),
    USERNAME_ALREADY_EXISTS(CONFLICT.value(), "이미 존재하는 사용자입니다."),
    STORE_NOT_FOUND(NOT_FOUND.value(), "존재하지 않는 매장입니다.");

    private final int status;
    private final String description;
}
