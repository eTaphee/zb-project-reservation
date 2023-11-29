package com.zeroboase.reservation.exception;

import lombok.Builder;

/**
 * 에러 응답
 *
 * @param status      상태 코드
 * @param errorCode   에러 코드
 * @param description 에러 설명
 */
@Builder
public record ErrorResponse(Integer status, ErrorCode errorCode, String description) {

    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .status(errorCode.getStatus())
            .errorCode(errorCode)
            .description(errorCode.getDescription())
            .build();
    }
}
