package com.zeroboase.reservation.controller;

import com.zeroboase.reservation.domain.Member;
import com.zeroboase.reservation.dto.StoreDto;
import com.zeroboase.reservation.dto.request.CreateStoreRequestDto;
import com.zeroboase.reservation.dto.request.UpdateStoreRequestDto;
import com.zeroboase.reservation.dto.response.CreateStoreResponseDto;
import com.zeroboase.reservation.service.PartnerStoreService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 파트너 상점 controller
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PARTNER')")
@RequestMapping("/partner/stores")
public class PartnerStoreController {

    private final PartnerStoreService partnerStoreService;

    @PostMapping
    public ResponseEntity<CreateStoreResponseDto> createStore(
        @Valid @RequestBody CreateStoreRequestDto request) {
        StoreDto store = partnerStoreService.createStore(request);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(store.id())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("@partnerStoreService.checkAccessReadStore(#id, authentication.principal)")
    @GetMapping("{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerStoreService.readStoreById(id));
    }

    @GetMapping
    public ResponseEntity<List<StoreDto>> getStoreList(Authentication authentication) {
        return ResponseEntity.ok(
            partnerStoreService.readStoreList((Member) authentication.getPrincipal())
        );
    }

    @PreAuthorize("@partnerStoreService.checkAccessUpdateStore(#id, authentication.principal)")
    @PatchMapping("{id}")
    public ResponseEntity<StoreDto> updateStore(@PathVariable Long id,
        @Valid @RequestBody UpdateStoreRequestDto request) {
        return ResponseEntity.ok(partnerStoreService.updateStore(id, request));
    }

    @PreAuthorize("@partnerStoreService.checkAccessDeleteStore(#id, authentication.principal)")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        partnerStoreService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
}
