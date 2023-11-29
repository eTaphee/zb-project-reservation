package com.zeroboase.reservation.domain.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;

/**
 * 예약 재고 수정
 */
@Builder
public record UpdateInventory(

) {

    /**
     * 예약 재고 수정 요청
     *
     * @param limitCount 예약 가능 수량
     */
    public record Request(@NotNull
                          @Positive
                          Integer limitCount) {

    }

    /**
     * 예약 재고 수정 응답
     *
     * @param id            재고 아아디
     * @param inventoryDate 예약 가능 날짜
     * @param inventoryTime 예약 가능 시간
     * @param limitCount    예약 가능 수량
     * @param createdAt     생성 일시
     */
    public record Response(Long id, LocalDate inventoryDate,
                           LocalTime inventoryTime, Integer limitCount,
                           LocalDateTime createdAt) {

    }

}
