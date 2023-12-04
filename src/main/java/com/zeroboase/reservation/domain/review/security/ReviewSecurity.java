package com.zeroboase.reservation.domain.review.security;

import static com.zeroboase.reservation.exception.ErrorCode.ACCESS_DENIED;
import static com.zeroboase.reservation.exception.ErrorCode.REVIEW_NOT_FOUND;

import com.zeroboase.reservation.configuration.security.AuthenticationFacade;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.review.entity.Review;
import com.zeroboase.reservation.domain.review.repository.ReviewRepository;
import com.zeroboase.reservation.exception.ReservationException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class ReviewSecurity {

    private final ReviewRepository reviewRepository;
    private final AuthenticationFacade authenticationFacade;

    public boolean checkAccessAsCustomer(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReservationException(REVIEW_NOT_FOUND));

        return checkAccess(review.getCreatedBy());
    }

    public boolean checkAccessAsPartner(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReservationException(REVIEW_NOT_FOUND));

        return checkAccess(review.getReservation().getInventory().getStore().getPartner());
    }

    private boolean checkAccess(Member member) {
        Member authenticatedMember = authenticationFacade.getAuthenticatedMember();

        if (!Objects.equals(authenticatedMember.getUsername(), member.getUsername())) {
            throw new ReservationException(ACCESS_DENIED);
        }

        return true;
    }
}
