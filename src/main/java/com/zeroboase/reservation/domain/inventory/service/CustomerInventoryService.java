package com.zeroboase.reservation.domain.inventory.service;

import com.zeroboase.reservation.domain.inventory.dto.model.CustomerInventoryDto;
import java.time.LocalDate;
import java.util.List;

public interface CustomerInventoryService {

    List<CustomerInventoryDto> getInventoryList(Long storeId, LocalDate inventoryDate);
}
