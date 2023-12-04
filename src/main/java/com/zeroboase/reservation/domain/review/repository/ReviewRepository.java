package com.zeroboase.reservation.domain.review.repository;

import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import com.zeroboase.reservation.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByReservation(Reservation reservation);

    Page<Review> findAllByReservationInventoryStoreId(Pageable pageable, Long storeId);
}
