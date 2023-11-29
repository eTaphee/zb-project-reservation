package com.zeroboase.reservation.controller;

import com.zeroboase.reservation.dto.CustomerInventoryDto;
import com.zeroboase.reservation.service.CustomerInventoryService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고객 재고 controller
 */
@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/customer/inventories")
public class CustomerInventoryController {

    private final CustomerInventoryService customerInventoryService;

    @GetMapping
    public ResponseEntity<List<CustomerInventoryDto>> getInventoryList(
        @RequestParam("store_id") Long storeId,
        @RequestParam("inventory_date") LocalDate inventoryDate) {
        return ResponseEntity.ok(customerInventoryService.getInventoryList(storeId, inventoryDate));
    }
}
