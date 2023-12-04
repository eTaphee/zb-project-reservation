package com.zeroboase.reservation.domain.reservation.controller;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.reservation.dto.model.PartnerReservationDto;
import com.zeroboase.reservation.domain.reservation.service.PartnerReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 파트너 예약 controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/partner/reservations")
public class PartnerReservationController {

    private final PartnerReservationService partnerReservationService;

    @PreAuthorize("hasRole('PARTNER') and @reservationSecurity.checkAccessAsPartner(#id)")
    @PostMapping("{id}/approve")
    public ResponseEntity<Void> approveReservation(@PathVariable Long id) {
        partnerReservationService.approveReservation(id);
        return ResponseEntity.ok(null);
    }

    @PreAuthorize("hasRole('PARTNER') and @reservationSecurity.checkAccessAsPartner(#id)")
    @PostMapping("{id}/reject")
    public ResponseEntity<Void> rejectReservation(@PathVariable Long id) {
        partnerReservationService.rejectReservation(id);
        return ResponseEntity.ok(null);
    }

    @PreAuthorize("hasRole('PARTNER') and @storeSecurity.checkAccessAsPartner(#storeId)")
    @GetMapping
    public ResponseEntity<PageResponse<PartnerReservationDto>> getReservationList(
        Long storeId,
        PageQuery query) {
        return ResponseEntity.ok(partnerReservationService.getReservationList(storeId, query));
    }
}
