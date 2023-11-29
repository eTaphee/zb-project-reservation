package com.zeroboase.reservation.service;

import com.zeroboase.reservation.dto.CustomerInventoryDto;
import java.time.LocalDate;
import java.util.List;

public interface CustomerInventoryService {

    List<CustomerInventoryDto> getInventoryList(Long storeId, LocalDate inventoryDate);
}
