package com.zeroboase.reservation.service;

import com.zeroboase.reservation.domain.Member;
import com.zeroboase.reservation.dto.PartnerInventoryDto;
import com.zeroboase.reservation.dto.request.partner.CreateInventoryRequestDto;
import com.zeroboase.reservation.dto.request.partner.UpdateInventoryRequestDto;
import com.zeroboase.reservation.dto.response.partner.CreateInventoryResponseDto;
import com.zeroboase.reservation.dto.response.partner.UpdateInventoryResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface PartnerInventoryService {

    CreateInventoryResponseDto createInventory(CreateInventoryRequestDto request);

    List<PartnerInventoryDto> getInventoryList(Long storeId, LocalDate inventoryDate);

    UpdateInventoryResponseDto updateInventory(Long id, UpdateInventoryRequestDto request);

    void deleteInventory(Long id);

    boolean checkAccessCreateInventory(Long storeId, Member member);

    boolean checkAccessReadInventory(Long storeId, Member member);

    boolean checkAccessUpdateInventory(Long id, Member member);

    boolean checkAccessDeleteInventory(Long id, Member member);
}
