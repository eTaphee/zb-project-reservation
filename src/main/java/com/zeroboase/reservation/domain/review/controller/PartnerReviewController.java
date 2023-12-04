package com.zeroboase.reservation.domain.review.controller;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.review.dto.model.PartnerReviewDto;
import com.zeroboase.reservation.domain.review.service.PartnerReviewService;
import com.zeroboase.reservation.util.ResponseEntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 파트너 리뷰 controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/partner/reviews")
public class PartnerReviewController {

    private final PartnerReviewService partnerReviewService;

    @PreAuthorize("hasRole('PARTNER') and @storeSecurity.checkAccessAsPartner(#storeId)")
    @GetMapping()
    public ResponseEntity<PageResponse<PartnerReviewDto>> getReviewList(PageQuery query,
        Long storeId) {
        return ResponseEntity.ok(partnerReviewService.getReviewList(query, storeId));
    }

    @PreAuthorize("hasRole('PARTNER') and @reviewSecurity.checkAccessAsPartner(#id)")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        partnerReviewService.deleteReview(id);
        return ResponseEntityUtil.noContent();
    }
}
