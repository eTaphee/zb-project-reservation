package com.zeroboase.reservation.controller;

import com.zeroboase.reservation.dto.PartnerStoreDto;
import com.zeroboase.reservation.dto.PartnerStoreInfoDto;
import com.zeroboase.reservation.dto.request.partner.CreateStoreRequestDto;
import com.zeroboase.reservation.dto.request.PageQueryDto;
import com.zeroboase.reservation.dto.request.partner.UpdateStoreRequestDto;
import com.zeroboase.reservation.dto.response.PageResponseDto;
import com.zeroboase.reservation.dto.response.partner.UpdateStoreResponseDto;
import com.zeroboase.reservation.service.PartnerStoreService;
import jakarta.validation.Valid;
import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 파트너 매장 controller
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PARTNER')")
@RequestMapping("/partner/stores")
public class PartnerStoreController {

    private final PartnerStoreService partnerStoreService;

    @PostMapping
    public ResponseEntity<Void> createStore(
        @Valid @RequestBody CreateStoreRequestDto request) {
        PartnerStoreInfoDto store = partnerStoreService.createStore(request);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(store.id())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('PARTNER') and @partnerStoreService.checkAccessReadStore(#id, authentication.principal)")
    @GetMapping("{id}")
    public ResponseEntity<PartnerStoreInfoDto> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerStoreService.getStoreInfo(id));
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<PartnerStoreDto>> getStoreList(PageQueryDto query) {
        return ResponseEntity.ok(partnerStoreService.getStoreList(query));
    }

    @PreAuthorize("hasRole('PARTNER') and @partnerStoreService.checkAccessUpdateStore(#id, authentication.principal)")
    @PatchMapping("{id}")
    public ResponseEntity<UpdateStoreResponseDto> updateStore(@PathVariable Long id,
        @Valid @RequestBody UpdateStoreRequestDto request) {
        return ResponseEntity.ok(partnerStoreService.updateStore(id, request));
    }

    @PreAuthorize("hasRole('PARTNER') and @partnerStoreService.checkAccessDeleteStore(#id, authentication.principal)")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        partnerStoreService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
}
