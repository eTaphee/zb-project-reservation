package com.zeroboase.reservation.dto;

import com.zeroboase.reservation.domain.Store;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record StoreDto(Long id, String name, String description, String address,
                       LocalDateTime registeredAt) {
    // TODO: partner 정보 추가

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
            .id(store.getId())
            .name(store.getName())
            .description(store.getDescription())
            .address(store.getAddress())
            .registeredAt(store.getRegisteredAt())
            .build();
    }
}
