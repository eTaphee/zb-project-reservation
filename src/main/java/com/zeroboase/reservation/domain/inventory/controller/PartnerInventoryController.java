package com.zeroboase.reservation.domain.inventory.controller;

import com.zeroboase.reservation.domain.inventory.dto.CreateInventory;
import com.zeroboase.reservation.domain.inventory.dto.UpdateInventory;
import com.zeroboase.reservation.domain.inventory.dto.model.PartnerInventoryDto;
import com.zeroboase.reservation.domain.inventory.service.PartnerInventoryService;
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
    public ResponseEntity<CreateInventory.Response> createInventory(
        @Valid @RequestBody CreateInventory.Request request) {
        CreateInventory.Response inventory = partnerInventoryService.createInventory(request);

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
    public ResponseEntity<UpdateInventory.Response> updateInventory(@PathVariable Long id,
        @Valid @RequestBody UpdateInventory request) {
        return ResponseEntity.ok(partnerInventoryService.updateInventory(id, request));
    }

    @PreAuthorize("hasRole('PARTNER') and @partnerInventoryService.checkAccessDeleteInventory(#id, authentication.principal)")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        partnerInventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
