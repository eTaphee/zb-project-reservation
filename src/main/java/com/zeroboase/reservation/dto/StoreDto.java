package com.zeroboase.reservation.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record StoreDto(Long id, String name, String description, String address,
                       Double latitude, Double longitude, LocalDateTime registeredAt) {
    // TODO: partner 정보 추가
}
