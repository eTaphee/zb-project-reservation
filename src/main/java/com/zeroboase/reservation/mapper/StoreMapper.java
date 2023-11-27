package com.zeroboase.reservation.mapper;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.zeroboase.reservation.domain.Store;
import com.zeroboase.reservation.dto.PartnerStoreInfoDto;
import com.zeroboase.reservation.dto.request.partner.UpdateStoreRequestDto;
import com.zeroboase.reservation.dto.response.partner.UpdateStoreResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = SPRING)
public interface StoreMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateStoreFromDto(UpdateStoreRequestDto dto, @MappingTarget Store store);

    PartnerStoreInfoDto map(Store store);

    UpdateStoreResponseDto mapToUpdateStoreResponse(Store store);
}
