package com.zeroboase.reservation.dto.response.partner;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;

/**
 * 예약 재고 등록 응답
 *
 * @param id            재고 아아디
 * @param inventoryDate 예약 가능 날짜
 * @param inventoryTime 예약 가능 시간
 * @param limitCount    예약 가능 수량
 * @param createdAt     생성 일시
 */
@Builder
public record CreateInventoryResponseDto(Long id, LocalDate inventoryDate,
                                         LocalTime inventoryTime, Integer limitCount,
                                         LocalDateTime createdAt) {

}