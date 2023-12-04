package com.zeroboase.reservation.domain.review.service.impl;

import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_IS_NOT_CHECKIN;
import static com.zeroboase.reservation.exception.ErrorCode.RESERVATION_NOT_FOUND;
import static com.zeroboase.reservation.exception.ErrorCode.REVIEW_ALREADY_EXISTS;
import static com.zeroboase.reservation.exception.ErrorCode.REVIEW_NOT_FOUND;

import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import com.zeroboase.reservation.domain.reservation.repository.ReservationRepository;
import com.zeroboase.reservation.domain.review.dto.CreateReview;
import com.zeroboase.reservation.domain.review.dto.model.CustomerReviewDto;
import com.zeroboase.reservation.domain.review.entity.Review;
import com.zeroboase.reservation.domain.review.mapper.ReviewMapper;
import com.zeroboase.reservation.domain.review.repository.ReviewRepository;
import com.zeroboase.reservation.domain.review.service.CustomerReviewService;
import com.zeroboase.reservation.domain.store.entity.Store;
import com.zeroboase.reservation.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 고객 리뷰 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewMapper reviewMapper;

    /**
     * 리뷰 작성
     *
     * @param request 리뷰 작성 요청
     * @return 리뷰 아이디
     */
    @Transactional
    @Override
    public Long createReview(CreateReview.Request request) {
        Reservation reservation = reservationRepository.findById(request.reservationId())
            .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        validateCreateReviewReservation(reservation);

        Store store = reservation.getInventory().getStore();
        store.increaseStarRating(request.starRating());

        return reviewRepository.save(Review.builder()
                .reservation(reservation)
                .content(request.content())
                .starRating(request.starRating())
                .build())
            .getId();
    }

    /**
     * 리뷰 조회
     *
     * @param id 리뷰 아이디
     * @return 리뷰 정보
     */
    @Transactional(readOnly = true)
    @Override
    public CustomerReviewDto getReview(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ReservationException(REVIEW_NOT_FOUND));

        return reviewMapper.mapToCustomerReview(review);
    }

    /**
     * 리뷰 삭제
     *
     * @param id 리뷰 아이디
     */
    @Transactional
    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ReservationException(REVIEW_NOT_FOUND));

        Store store = review.getReservation().getInventory().getStore();
        store.decreaseStarRating(review.getStarRating());

        reviewRepository.deleteById(id);
    }

    /**
     * 리뷰 작성 예약 유효성 검서
     *
     * @param reservation 예약 정보
     */
    private void validateCreateReviewReservation(Reservation reservation) {
        if (reviewRepository.existsByReservation(reservation)) {
            throw new ReservationException(REVIEW_ALREADY_EXISTS);
        }

        if (!reservation.isCheckedIn()) {
            throw new ReservationException(RESERVATION_IS_NOT_CHECKIN);
        }
    }
}
