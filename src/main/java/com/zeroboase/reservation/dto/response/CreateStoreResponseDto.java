package com.zeroboase.reservation.dto.response;

import com.zeroboase.reservation.dto.StoreDto;
import lombok.Builder;

/**
 * 매장 등록 응답 dto
 *
 * @param name        매장 이름
 * @param description 매장 설명
 * @param address     매장 주소
 */
@Builder
public record CreateStoreResponseDto(Long id, String name, String description, String address) {

    public static CreateStoreResponseDto from(StoreDto store) {
        return CreateStoreResponseDto.builder()
            .id(store.id())
            .name(store.name())
            .description(store.description())
            .address(store.address())
            .build();
    }
}
