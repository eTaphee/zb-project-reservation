package com.zeroboase.reservation.dto.request.partner;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

/**
 * 예약 재고 수정 요청
 *
 * @param limitCount 예약 가능 수량
 */
@Builder
public record UpdateInventoryRequestDto(
    @NotNull
    @Positive
    Integer limitCount
) {

}
