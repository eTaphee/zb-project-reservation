package com.zeroboase.reservation.domain.inventory.service;

import com.zeroboase.reservation.domain.inventory.dto.CreateInventory;
import com.zeroboase.reservation.domain.inventory.dto.UpdateInventory;
import com.zeroboase.reservation.domain.inventory.dto.model.PartnerInventoryDto;
import com.zeroboase.reservation.domain.member.entity.Member;
import java.time.LocalDate;
import java.util.List;

public interface PartnerInventoryService {

    CreateInventory.Response createInventory(CreateInventory.Request request);

    List<PartnerInventoryDto> getInventoryList(Long storeId, LocalDate inventoryDate);

    UpdateInventory.Response updateInventory(Long id, UpdateInventory request);

    void deleteInventory(Long id);

    boolean checkAccessCreateInventory(Long storeId, Member member);

    boolean checkAccessReadInventory(Long storeId, Member member);

    boolean checkAccessUpdateInventory(Long id, Member member);

    boolean checkAccessDeleteInventory(Long id, Member member);
}
