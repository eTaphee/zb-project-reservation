package com.zeroboase.reservation.domain.reservation.repository;

import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_NOT_FOUND;

import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import com.zeroboase.reservation.exception.ReservationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByReservedBy(Pageable pageable, Member reservedBy);

    Page<Reservation> findAllByInventoryStoreId(Pageable pageable, Long storeId);

    default Reservation findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
    }
}
