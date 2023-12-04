package com.zeroboase.reservation.domain.reservation.dto;

import com.zeroboase.reservation.validator.annotation.Telephone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 매장 예약 체크인
 */
public record CheckIn() {

    /**
     * 매장 예약 체크인 요청
     *
     * @param storeId              매장 아이디(kiosk)
     * @param reservationId        예약 아이디
     * @param reservationPeronName 예약자 이름
     * @param reservationPersonTel 예약자 연락처
     */
    public record Request(
        @NotNull Long storeId,
        @NotNull Long reservationId,
        @NotBlank String reservationPeronName,
        @Telephone String reservationPersonTel
    ) {

    }
}
