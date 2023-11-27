package com.zeroboase.reservation.controller;


import com.zeroboase.reservation.dto.CustomerStoreDto;
import com.zeroboase.reservation.dto.CustomerStoreInfoDto;
import com.zeroboase.reservation.dto.request.PageQueryDto;
import com.zeroboase.reservation.dto.response.PageResponseDto;
import com.zeroboase.reservation.service.CustomerStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고객 매장 controller
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/customer/stores")
public class CustomerStoreController {

    private final CustomerStoreService customerStoreService;

    @GetMapping
    public ResponseEntity<PageResponseDto<CustomerStoreDto>> getStoreList(
        PageQueryDto query,
        Double latitude,
        Double longitude
    ) {
        return ResponseEntity.ok(
            customerStoreService.getStoreList(query, latitude, longitude)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerStoreInfoDto> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(customerStoreService.getStoreInfo(id));
    }
}
