package com.zeroboase.reservation.domain.review.repository;

import static com.zeroboase.reservation.exception.ErrorCode.REVIEW_NOT_FOUND;

import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import com.zeroboase.reservation.domain.review.entity.Review;
import com.zeroboase.reservation.exception.ReservationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByReservation(Reservation reservation);

    Page<Review> findAllByReservationInventoryStoreId(Pageable pageable, Long storeId);

    default Review findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ReservationException(REVIEW_NOT_FOUND));
    }
}
