package com.zeroboase.reservation.mapper;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.zeroboase.reservation.domain.Store;
import com.zeroboase.reservation.dto.StoreDto;
import com.zeroboase.reservation.dto.request.UpdateStoreRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

// TODO: dto<->entity 변환 mapper로 수정해야하나?

@Mapper(componentModel = SPRING)
public interface StoreMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateStoreFromDto(UpdateStoreRequestDto dto, @MappingTarget Store store);

    StoreDto map(Store store);
}
