package com.zeroboase.reservation.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400
    VALIDATION_FAIL(BAD_REQUEST.value(), "유효성 검사 실패"),
    RESERVATION_CLOSED(BAD_REQUEST.value(), "예약이 마감됐습니다."),
    RESERVE_TIME_EXCEED(BAD_REQUEST.value(), "예약 가능 시간을 초과했습니다."),
    CANCEL_RESERVATION_TIME_EXCEED(BAD_REQUEST.value(), "예약 취소 가능 시간이 지났습니다."),
    CHECKIN_TIME_EXCEED(BAD_REQUEST.value(), "체크인 가능 시간이 지났습니다."),
    CHECKIN_TIME_EARLY(BAD_REQUEST.value(), "체크인 시간이 이릅니다."),
    RESERVATION_ALREADY_CANCELED(BAD_REQUEST.value(), "이미 취소된 예약입니다."),
    RESERVATION_ALREADY_REJECTED(BAD_REQUEST.value(), "이미 거절된 예약입니다."),
    RESERVATION_ALREADY_APPROVED(BAD_REQUEST.value(), "이미 승인된 예약입니다."),
    RESERVATION_ALREADY_CHECKIN(BAD_REQUEST.value(), "이미 체크인된 예약입니다."),
    RESERVATION_WAIT_FOR_APPROVAL(BAD_REQUEST.value(), "승인을 기다리는 예약입니다."),
    RESERVATION_INFO_NOT_MATCHED(BAD_REQUEST.value(), "예약 정보가 불일치 합니다."),
    RESERVATION_IS_NOT_WAIT(BAD_REQUEST.value(), "대기 상태가 아닙니다."),
    RESERVATION_IS_NOT_CHECKIN(BAD_REQUEST.value(), "체크인 되지 않은 예약입니다."),


    // 401
    TOKEN_EXPIRED(UNAUTHORIZED.value(), "토큰이 만료됐습니다."),
    JWT_ERROR(UNAUTHORIZED.value(), "JWT 예외가 발생했습니다."),
    AUTHENTICATE_FAIL(UNAUTHORIZED.value(), "사용자 인증을 실패했습니다."),
    USERNAME_NOT_FOUND(UNAUTHORIZED.value(), "존재하지 않는 사용자입니다."),

    // 403
    ACCESS_DENIED(FORBIDDEN.value(), "접근 권한이 없습니다."),
    CREATE_INVENTORY_FORBIDDEN(FORBIDDEN.value(), "등록 권한이 없습니다."),
    READ_INVENTORY_FORBIDDEN(FORBIDDEN.value(), "읽기 권한이 없습니다."),
    UPDATE_INVENTORY_FORBIDDEN(FORBIDDEN.value(), "읽기 권한이 없습니다."),
    DELETE_INVENTORY_FORBIDDEN(FORBIDDEN.value(), "읽기 권한이 없습니다."),

    // 404
    STORE_NOT_FOUND(NOT_FOUND.value(), "존재하지 않는 매장입니다."),
    INVENTORY_NOT_FOUND(NOT_FOUND.value(), "존재하지 않는 재고입니다."),
    RESERVATION_NOT_FOUND(NOT_FOUND.value(), "존재하지 않는 예약입니다."),
    REVIEW_NOT_FOUND(NOT_FOUND.value(), "존재하지 않는 리뷰입니다."),

    // 409
    USERNAME_ALREADY_EXISTS(CONFLICT.value(), "이미 존재하는 사용자입니다."),
    INVENTORY_ALREADY_EXISTS(CONFLICT.value(), "이미 존재하는 재고입니다."),
    RESERVATION_ALREADY_EXISTS(CONFLICT.value(), "예약정보가 이미 존재합니다."),
    REVIEW_ALREADY_EXISTS(CONFLICT.value(), "리뷰 작성 완료된 예약입니다."),

    // 500
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR.value(), "내부 서버 오류 발생");

    /**
     * 상태 코드
     */
    private final int status;

    /**
     * 에러 코드 설명
     */
    private final String description;
}
