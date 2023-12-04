package com.zeroboase.reservation.domain.reservation.service.impl;

import static com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus.WAIT;
import static com.zeroboase.reservation.exception.ErrorCode.CANCEL_RESERVATION_TIME_EXCEED;
import static com.zeroboase.reservation.exception.ErrorCode.CHECKIN_TIME_EARLY;
import static com.zeroboase.reservation.exception.ErrorCode.CHECKIN_TIME_EXCEED;
import static com.zeroboase.reservation.exception.ErrorCode.INVENTORY_NOT_FOUND;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_ALREADY_CANCELED;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_ALREADY_CHECKIN;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_ALREADY_REJECTED;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_CLOSED;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_INFO_NOT_MATCHED;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_NOT_FOUND;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_WAIT_FOR_APPROVAL;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVE_TIME_EXCEED;

import com.zeroboase.reservation.configuration.security.AuthenticationFacade;
import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.inventory.entity.Inventory;
import com.zeroboase.reservation.domain.inventory.repository.InventoryRepository;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.reservation.dto.CheckIn;
import com.zeroboase.reservation.domain.reservation.dto.Reserve;
import com.zeroboase.reservation.domain.reservation.dto.model.CustomerReservationDto;
import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import com.zeroboase.reservation.domain.reservation.mapper.ReservationMapper;
import com.zeroboase.reservation.domain.reservation.repository.ReservationRepository;
import com.zeroboase.reservation.domain.reservation.service.CustomerReservationService;
import com.zeroboase.reservation.exception.ReservationException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 고객 예약 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomerReservationServiceImpl implements CustomerReservationService {

    private final AuthenticationFacade authenticationFacade;
    private final ReservationRepository reservationRepository;
    private final InventoryRepository inventoryRepository;
    private final ReservationMapper reservationMapper;

    @Value("${options.maxAllowReserveTime}")
    private Long maxAllowReserveTime;

    @Value("${options.maxAllowCancelReservationTime}")
    private Long maxAllowCancelReservationTime;

    @Value("${options.allowCheckInTime}")
    private Long AllowCheckInTime;

    /**
     * 예약
     *
     * @param request 예약 요청
     * @return 예약 아이디
     */
    @Transactional
    @Override
    public Long reserve(Reserve.Request request) {
        // TODO lock 필요 lock invid

        Inventory inventory = inventoryRepository.findById(request.inventoryId())
            .orElseThrow(() -> new ReservationException(INVENTORY_NOT_FOUND));

        Member member = authenticationFacade.getAuthenticatedMember();

        validateReserve(inventory);

        Reservation reservation = reservationRepository.save(
            request.toEntity(inventory, member)
        );

        inventory.setAvailableCount(inventory.getAvailableCount() - 1);

        // send message aop

        return reservation.getId();
    }

    /**
     * 예약 취소
     *
     * @param id 예약 아이디
     */
    @Transactional
    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = findReservationOrElseThrow(id);

        validateCancelReservation(reservation);

        reservation.cancel();

        // send message aop
    }

    /**
     * 예약 조회
     *
     * @param id 예약 아이디
     * @return 예약 정보
     */
    @Transactional(readOnly = true)
    @Override
    public CustomerReservationDto getReservation(Long id) {
        return reservationMapper.mapToCustomerReservation(findReservationOrElseThrow(id));
    }

    /**
     * 예약 목록 조회
     *
     * @param query 페이징 쿼리
     * @return 페이징된 예약 목록
     */
    @Transactional(readOnly = true)
    @Override
    public PageResponse<CustomerReservationDto> getReservationList(PageQuery query) {
        Member member = authenticationFacade.getAuthenticatedMember();
        return PageResponse.from(
            reservationRepository.findAllByReservedBy(query.toPageable(), member)
                .map(reservationMapper::mapToCustomerReservation)
        );
    }

    /**
     * 예약 체크인
     *
     * @param request 체크인 요청
     */
    @Transactional
    @Override
    public void checkIn(CheckIn.Request request) {
        Reservation reservation = findReservationOrElseThrow(request.reservationId());

        validateCheckIn(reservation, request);

        reservation.checkIn();
    }

    /**
     * 예약 유효성 검사
     *
     * @param inventory 예약 재고 정보
     */
    private void validateReserve(Inventory inventory) {
        if (inventory.getAvailableCount() <= 0) {
            throw new ReservationException(RESERVATION_CLOSED);
        }

        if (LocalDateTime.now()
            .plusMinutes(maxAllowReserveTime)
            .isAfter(inventory.getInventoryDateTime())) {
            throw new ReservationException(RESERVE_TIME_EXCEED);
        }
    }

    /**
     * 예약 취소 유효성 검사
     *
     * @param reservation 예약 정보
     */
    private void validateCancelReservation(Reservation reservation) {
        validateReservationStatus(reservation);

        if (LocalDateTime.now()
            .plusMinutes(maxAllowCancelReservationTime)
            .isAfter(reservation.getInventory().getInventoryDateTime())) {
            throw new ReservationException(CANCEL_RESERVATION_TIME_EXCEED);
        }
    }

    /**
     * 체크인 유효성 검사
     *
     * @param reservation 예약 정보
     * @param request     체크인 정보
     */
    private void validateCheckIn(Reservation reservation, CheckIn.Request request) {
        validateReservationStatus(reservation);

        if (reservation.getStatus() == WAIT) {
            throw new ReservationException(RESERVATION_WAIT_FOR_APPROVAL);
        }

        LocalDateTime checkInTime = LocalDateTime.now();
        LocalDateTime reserveTime = reservation.getInventory().getInventoryDateTime();

        if (checkInTime.isAfter(reserveTime)) {
            throw new ReservationException(CHECKIN_TIME_EXCEED);
        }

        if (checkInTime.minusMinutes(AllowCheckInTime).isBefore(reserveTime)) {
            throw new ReservationException(CHECKIN_TIME_EARLY);
        }

        if (reservation.isCheckedIn()) {
            throw new ReservationException(RESERVATION_ALREADY_CHECKIN);
        }

        if (!reservation.getReservationPersonTel().equals(request.reservationPersonTel())
            || !reservation.getReservationPeronName().equals(request.reservationPeronName())) {
            throw new ReservationException(RESERVATION_INFO_NOT_MATCHED);
        }
    }

    /**
     * 예약 상태 유효성 검사
     *
     * @param reservation 예약 정보
     */
    private void validateReservationStatus(Reservation reservation) {
        switch (reservation.getStatus()) {
            case CANCEL -> throw new ReservationException(RESERVATION_ALREADY_CANCELED);
            case REJECT -> throw new ReservationException(RESERVATION_ALREADY_REJECTED);
        }
    }


    private Reservation findReservationOrElseThrow(Long id) {
        return reservationRepository.findById(id)
            .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
    }
}
