package com.zeroboase.reservation.domain.reservation.service;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.reservation.dto.model.PartnerReservationDto;

public interface PartnerReservationService {

    void approveReservation(Long id);

    void rejectReservation(Long id);

    PageResponse<PartnerReservationDto> getReservationList(Long storeId, PageQuery query);
}
