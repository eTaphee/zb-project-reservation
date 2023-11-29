package com.zeroboase.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

/**
 * 고객 예약 재고 정보
 * @param id 예약 아이디
 * @param inventoryDate 예약 가능 일자
 * @param inventoryTime 예약 가능 시간
 * @param limitCount 예약 가능 수량
 * @param availableCount 예약 남은 수량
 */
@Builder
public record CustomerInventoryDto(Long id, LocalDate inventoryDate, LocalTime inventoryTime,
                                   Integer limitCount, Integer availableCount) {

}
