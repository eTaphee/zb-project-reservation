package com.zeroboase.reservation.mapper;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.zeroboase.reservation.domain.store.dto.UpdateStore;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreInfoDto;
import com.zeroboase.reservation.domain.store.entity.Store;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = SPRING)
public interface StoreMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateStoreFromDto(UpdateStore.Request dto, @MappingTarget Store store);

    PartnerStoreInfoDto map(Store store);

    UpdateStore.Response mapToUpdateStoreResponse(Store store);
}
