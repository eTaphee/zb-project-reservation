package com.zeroboase.reservation.domain.review.service.impl;

import static com.zeroboase.reservation.exception.ErrorCode.REVIEW_NOT_FOUND;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.review.dto.model.PartnerReviewDto;
import com.zeroboase.reservation.domain.review.entity.Review;
import com.zeroboase.reservation.domain.review.mapper.ReviewMapper;
import com.zeroboase.reservation.domain.review.repository.ReviewRepository;
import com.zeroboase.reservation.domain.review.service.PartnerReviewService;
import com.zeroboase.reservation.domain.store.entity.Store;
import com.zeroboase.reservation.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 파트너 리뷰 서비스
 */
@Service
@RequiredArgsConstructor
public class PartnerReviewServiceImpl implements PartnerReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    /**
     * 리뷰 목록 조회
     *
     * @param query   페이징 쿼리
     * @param storeId 매장 아이디
     * @return 리뷰 목록
     */
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PartnerReviewDto> getReviewList(PageQuery query, Long storeId) {
        return PageResponse.from(
            reviewRepository.findAllByReservationInventoryStoreId(query.toPageable(), storeId)
                .map(reviewMapper::mapToPartnerReview)
        );
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
}
