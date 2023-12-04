package com.zeroboase.reservation.domain.reservation.controller;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.reservation.dto.CheckIn;
import com.zeroboase.reservation.domain.reservation.dto.Reserve;
import com.zeroboase.reservation.domain.reservation.dto.model.CustomerReservationDto;
import com.zeroboase.reservation.domain.reservation.service.CustomerReservationService;
import com.zeroboase.reservation.util.ResponseEntityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고객 예약 controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/reservations")
public class CustomerReservationController {

    private final CustomerReservationService customerReservationService;

    /**
     * 예약
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<Void> reserve(@Valid @RequestBody Reserve.Request request) {
        Long reservationId = customerReservationService.reserve(request);
        return ResponseEntityUtil.created(reservationId);
    }

    /**
     * 예약 취소
     */
    @PreAuthorize("hasRole('CUSTOMER') and @reservationSecurity.checkAccessAsCustomer(#id)")
    @PostMapping("{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        customerReservationService.cancelReservation(id);
        return ResponseEntity.ok(null);
    }

    /**
     * 예약 조회
     */
    @PreAuthorize("hasRole('CUSTOMER') and @reservationSecurity.checkAccessAsCustomer(#id)")
    @GetMapping("{id}")
    public ResponseEntity<CustomerReservationDto> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(customerReservationService.getReservation(id));
    }

    /**
     * 예약 목록 조회
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<PageResponse<CustomerReservationDto>> getReservationList(
        PageQuery query) {
        return ResponseEntity.ok(customerReservationService.getReservationList(query));
    }

    /**
     * 체크인(방문)
     */
    @PostMapping("checkin")
    public ResponseEntity<String> checkIn(@Valid @RequestBody CheckIn.Request request) {
        customerReservationService.checkIn(request);
        return ResponseEntity.ok(null);
    }
}
