package com.zeroboase.reservation.domain.reservation.service;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.reservation.dto.CheckIn;
import com.zeroboase.reservation.domain.reservation.dto.Reserve;
import com.zeroboase.reservation.domain.reservation.dto.model.CustomerReservationDto;

public interface CustomerReservationService {

    Long reserve(Reserve.Request request);

    void cancelReservation(Long id);

    CustomerReservationDto getReservation(Long id);

    PageResponse<CustomerReservationDto> getReservationList(PageQuery query);

    void checkIn(CheckIn.Request request);
}
