package com.zeroboase.reservation.controller;

import com.zeroboase.reservation.dto.PartnerInventoryDto;
import com.zeroboase.reservation.dto.request.partner.CreateInventoryRequestDto;
import com.zeroboase.reservation.dto.request.partner.UpdateInventoryRequestDto;
import com.zeroboase.reservation.dto.response.partner.CreateInventoryResponseDto;
import com.zeroboase.reservation.dto.response.partner.UpdateInventoryResponseDto;
import com.zeroboase.reservation.service.PartnerInventoryService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 파트너 재고 controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/inventories")
public class PartnerInventoryController {

    private final PartnerInventoryService partnerInventoryService;

    @PreAuthorize("hasRole('PARTNER') and @partnerInventoryService.checkAccessCreateInventory(#request.storeId(), authentication.principal)")
    @PostMapping
    public ResponseEntity<CreateInventoryResponseDto> createInventory(
        @Valid @RequestBody CreateInventoryRequestDto request) {
        CreateInventoryResponseDto inventory = partnerInventoryService.createInventory(request);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(inventory.id())
            .toUri();

        return ResponseEntity.created(location).body(inventory);
    }

    @PreAuthorize("hasRole('PARTNER') and @partnerInventoryService.checkAccessReadInventory(#storeId, authentication.principal)")
    @GetMapping
    public ResponseEntity<List<PartnerInventoryDto>> getInventoryList(
        @RequestParam("store_id") Long storeId,
        @RequestParam("inventory_date") LocalDate inventoryDate) {
        return ResponseEntity.ok(partnerInventoryService.getInventoryList(storeId, inventoryDate));
    }

    @PreAuthorize("hasRole('PARTNER') and @partnerInventoryService.checkAccessUpdateInventory(#id, authentication.principal)")
    @PatchMapping("{id}")
    public ResponseEntity<UpdateInventoryResponseDto> updateInventory(@PathVariable Long id,
        @Valid @RequestBody UpdateInventoryRequestDto request) {
        return ResponseEntity.ok(partnerInventoryService.updateInventory(id, request));
    }

    @PreAuthorize("hasRole('PARTNER') and @partnerInventoryService.checkAccessDeleteInventory(#id, authentication.principal)")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        partnerInventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
