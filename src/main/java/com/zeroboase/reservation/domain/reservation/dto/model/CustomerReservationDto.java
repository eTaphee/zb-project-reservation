package com.zeroboase.reservation.domain.reservation.dto.model;

import com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CustomerReservationDto(
    Long id,
    ReserveStatus status,
    LocalDate reservationDate,
    LocalTime reservationTime,
    String reservationPersonTel,
    String reservationPeronName,
    Integer persons,
    LocalDateTime reservedAt
) {

}
