package com.zeroboase.reservation.domain.reservation.security;

import static com.zeroboase.reservation.exception.ErrorCode.ACCESS_DENIED;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_NOT_FOUND;

import com.zeroboase.reservation.configuration.security.AuthenticationFacade;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import com.zeroboase.reservation.domain.reservation.repository.ReservationRepository;
import com.zeroboase.reservation.exception.ReservationException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class ReservationSecurity {

    private final ReservationRepository reservationRepository;
    private final AuthenticationFacade authenticationFacade;

    public boolean checkAccessAsCustomer(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        return checkAccess(reservation.getReservedBy());
    }

    public boolean checkAccessAsPartner(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        return checkAccess(reservation.getInventory().getStore().getPartner());
    }

    private boolean checkAccess(Member member) {
        Member authenticatedMember = authenticationFacade.getAuthenticatedMember();

        if (!Objects.equals(authenticatedMember.getUsername(), member.getUsername())) {
            throw new ReservationException(ACCESS_DENIED);
        }

        return true;
    }
}
