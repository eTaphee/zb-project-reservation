package com.zeroboase.reservation.domain.inventory.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.zeroboase.reservation.domain.inventory.dto.CreateInventory;
import com.zeroboase.reservation.domain.inventory.dto.UpdateInventory;
import com.zeroboase.reservation.domain.inventory.entity.Inventory;
import com.zeroboase.reservation.domain.inventory.dto.model.CustomerInventoryDto;
import com.zeroboase.reservation.domain.inventory.dto.model.PartnerInventoryDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = SPRING)
public interface InventoryMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateInventoryFromDto(UpdateInventory dto, @MappingTarget Inventory inventory);

    PartnerInventoryDto mapToPartnerInventory(Inventory inventory);

    CustomerInventoryDto mapToCustomerInventory(Inventory inventory);

    CreateInventory.Response mapToCreateInventoryResponse(Inventory inventory);

    UpdateInventory.Response mapToUpdateInventoryResponse(Inventory inventory);
}
