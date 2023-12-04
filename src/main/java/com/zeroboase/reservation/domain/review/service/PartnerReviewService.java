package com.zeroboase.reservation.domain.review.service;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.review.dto.model.PartnerReviewDto;

public interface PartnerReviewService {

    PageResponse<PartnerReviewDto> getReviewList(PageQuery query, Long storeId);

    void deleteReview(Long id);
}
