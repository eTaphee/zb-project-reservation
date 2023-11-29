package com.zeroboase.reservation.domain.reservation.controller;

import com.zeroboase.reservation.domain.reservation.service.CustomerReservationService;
import lombok.RequiredArgsConstructor;
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
}
