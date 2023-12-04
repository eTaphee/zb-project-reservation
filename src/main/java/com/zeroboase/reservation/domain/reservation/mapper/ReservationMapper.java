package com.zeroboase.reservation.domain.reservation.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.zeroboase.reservation.domain.reservation.dto.model.CustomerReservationDto;
import com.zeroboase.reservation.domain.reservation.dto.model.PartnerReservationDto;
import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ReservationMapper {

    @Mapping(source = "reservation.inventory.inventoryDate", target = "reservationDate")
    @Mapping(source = "reservation.inventory.inventoryTime", target = "reservationTime")
    CustomerReservationDto mapToCustomerReservation(Reservation reservation);

    @Mapping(source = "reservation.inventory.inventoryDate", target = "reservationDate")
    @Mapping(source = "reservation.inventory.inventoryTime", target = "reservationTime")
    PartnerReservationDto mapToPartnerReservation(Reservation reservation);
}
