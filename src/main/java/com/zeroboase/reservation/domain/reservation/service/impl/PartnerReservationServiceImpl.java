package com.zeroboase.reservation.domain.reservation.service.impl;

import static com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus.APPROVE;
import static com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus.CANCEL;
import static com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus.REJECT;
import static com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus.WAIT;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_ALREADY_APPROVED;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_ALREADY_CANCELED;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_ALREADY_REJECTED;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_IS_NOT_WAIT;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_NOT_FOUND;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.reservation.dto.model.PartnerReservationDto;
import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import com.zeroboase.reservation.domain.reservation.mapper.ReservationMapper;
import com.zeroboase.reservation.domain.reservation.repository.ReservationRepository;
import com.zeroboase.reservation.domain.reservation.service.PartnerReservationService;
import com.zeroboase.reservation.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 파트너 예약 서비스
 */
@Service
@RequiredArgsConstructor
public class PartnerReservationServiceImpl implements PartnerReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    /**
     * 예약 승인
     *
     * @param id 예약 아이디
     */
    @Transactional
    @Override
    public void approveReservation(Long id) {
        Reservation reservation = findReservationOrElseThrow(id);

        validateReservationStatus(reservation);

        reservation.approve();

        // message
    }

    /**
     * 예약 거절
     *
     * @param id 예약 아이디
     */
    @Transactional
    @Override
    public void rejectReservation(Long id) {
        Reservation reservation = findReservationOrElseThrow(id);

        validateReservationStatus(reservation);

        reservation.reject();

        // message
    }

    /**
     * 예약 조회
     *
     * @param storeId 매장 아이디
     * @param query   페이징 쿼리
     * @return 페이징된 예약 목록
     */
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PartnerReservationDto> getReservationList(Long storeId, PageQuery query) {
        return PageResponse.from(
            reservationRepository.findAllByInventoryStoreId(query.toPageable(), storeId)
                .map(reservationMapper::mapToPartnerReservation)
        );
    }

    /**
     * 예약 상태 유효성 검사
     *
     * @param reservation 예약 정보
     */
    private void validateReservationStatus(Reservation reservation) {
        if (reservation.getStatus() == APPROVE) {
            throw new ReservationException(RESERVATION_ALREADY_APPROVED);
        }

        if (reservation.getStatus() == REJECT) {
            throw new ReservationException(RESERVATION_ALREADY_REJECTED);
        }

        if (reservation.getStatus() == CANCEL) {
            throw new ReservationException(RESERVATION_ALREADY_CANCELED);
        }

        if (reservation.getStatus() != WAIT) {
            throw new ReservationException(RESERVATION_IS_NOT_WAIT);
        }
    }

    private Reservation findReservationOrElseThrow(Long id) {
        return reservationRepository.findById(id)
            .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
    }
}
