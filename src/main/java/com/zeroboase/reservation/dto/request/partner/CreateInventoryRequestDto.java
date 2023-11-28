package com.zeroboase.reservation.dto.request.partner;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

/**
 * 예약 재고 등록 요청
 *
 * @param storeId       매장 아이디
 * @param inventoryDate 예약 가능 날짜
 * @param inventoryTime 예약 가능 시간
 * @param limitCount    예약 가능 수량
 */
@Builder
public record CreateInventoryRequestDto(
    @NotNull
    Long storeId,

    @NotNull
    @FutureOrPresent
    LocalDate inventoryDate,

    @NotNull
    @FutureOrPresent
    LocalTime inventoryTime,

    @NotNull
    @Positive
    Integer limitCount
) {

}
