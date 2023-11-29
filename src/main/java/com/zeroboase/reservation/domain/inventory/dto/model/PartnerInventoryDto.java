package com.zeroboase.reservation.domain.inventory.dto.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;

/**
 * 파트너 예약 재고 정보
 *
 * @param id             재고 아이디
 * @param inventoryDate  예약 가능 날짜
 * @param inventoryTime  예약 가능 시간
 * @param limitCount     예약 가능 수량
 * @param availableCount 예약 남은 수량
 * @param createdAt      재고 등록일시
 */
@Builder
public record PartnerInventoryDto(Long id, LocalDate inventoryDate, LocalTime inventoryTime,
                                  Integer limitCount, Integer availableCount,
                                  LocalDateTime createdAt) {

}
