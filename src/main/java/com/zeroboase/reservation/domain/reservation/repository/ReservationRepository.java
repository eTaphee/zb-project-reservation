package com.zeroboase.reservation.domain.reservation.repository;

import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByReservedBy(Pageable pageable, Member reservedBy);

    Page<Reservation> findAllByInventoryStoreId(Pageable pageable, Long storeId);
}
