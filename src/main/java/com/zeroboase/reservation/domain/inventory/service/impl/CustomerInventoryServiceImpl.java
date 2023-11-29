package com.zeroboase.reservation.domain.inventory.service.impl;

import com.zeroboase.reservation.domain.inventory.dto.model.CustomerInventoryDto;
import com.zeroboase.reservation.mapper.InventoryMapper;
import com.zeroboase.reservation.domain.inventory.repository.InventoryRepository;
import com.zeroboase.reservation.domain.inventory.service.CustomerInventoryService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 고객 예약 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomerInventoryServiceImpl implements CustomerInventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CustomerInventoryDto> getInventoryList(Long storeId, LocalDate inventoryDate) {
        return inventoryRepository.findAllByStoreIdAndInventoryDateOrderByInventoryTime(storeId,
                inventoryDate)
            .stream()
            .map(inventoryMapper::mapToCustomerInventory)
            .collect(Collectors.toList());
    }
}
