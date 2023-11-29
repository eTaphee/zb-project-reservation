package com.zeroboase.reservation.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.zeroboase.reservation.domain.Inventory;
import com.zeroboase.reservation.dto.CustomerInventoryDto;
import com.zeroboase.reservation.dto.PartnerInventoryDto;
import com.zeroboase.reservation.dto.request.partner.UpdateInventoryRequestDto;
import com.zeroboase.reservation.dto.response.partner.CreateInventoryResponseDto;
import com.zeroboase.reservation.dto.response.partner.UpdateInventoryResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = SPRING)
public interface InventoryMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateInventoryFromDto(UpdateInventoryRequestDto dto, @MappingTarget Inventory inventory);

    PartnerInventoryDto mapToPartnerInventory(Inventory inventory);

    CustomerInventoryDto mapToCustomerInventory(Inventory inventory);

    CreateInventoryResponseDto mapToCreateInventoryResponse(Inventory inventory);

    UpdateInventoryResponseDto mapToUpdateInventoryResponse(Inventory inventory);
}
