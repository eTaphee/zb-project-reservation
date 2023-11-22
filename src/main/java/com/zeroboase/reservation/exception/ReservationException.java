package com.zeroboase.reservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationException extends RuntimeException {

    private final ErrorCode errorCode;
}
