package com.zeroboase.reservation.exception;

import lombok.Builder;

/**
 * 에러 응답 dto
 *
 * @param status      상태 코드
 * @param errorCode   에러 코드
 * @param description 에러 설명
 */
@Builder
public record ErrorResponseDto(Integer status, ErrorCode errorCode, String description) {

    public static ErrorResponseDto from(ErrorCode errorCode) {
        return ErrorResponseDto.builder()
            .status(errorCode.getStatus())
            .errorCode(errorCode)
            .description(errorCode.getDescription())
            .build();
    }
}
