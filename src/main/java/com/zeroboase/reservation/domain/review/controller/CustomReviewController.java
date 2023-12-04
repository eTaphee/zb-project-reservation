package com.zeroboase.reservation.domain.review.controller;

import com.zeroboase.reservation.domain.review.dto.CreateReview;
import com.zeroboase.reservation.domain.review.dto.model.CustomerReviewDto;
import com.zeroboase.reservation.domain.review.service.CustomerReviewService;
import com.zeroboase.reservation.util.ResponseEntityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고객 리뷰 controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/reviews")
public class CustomReviewController {

    private final CustomerReviewService customerReviewService;

    @PreAuthorize("hasRole('CUSTOMER') and @reservationSecurity.checkAccessAsCustomer(#request.reservationId)")
    @PostMapping()
    public ResponseEntity<Void> createReview(
        @Valid @RequestBody CreateReview.Request request) {
        return ResponseEntityUtil.created(customerReviewService.createReview(request));
    }

    @PreAuthorize("hasRole('CUSTOMER') and @reviewSecurity.checkAccessAsCustomer(#id)")
    @GetMapping("{id}")
    public ResponseEntity<CustomerReviewDto> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(customerReviewService.getReview(id));
    }

    @PreAuthorize("hasRole('CUSTOMER') and @reviewSecurity.checkAccessAsCustomer(#id)")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        customerReviewService.deleteReview(id);
        return ResponseEntityUtil.noContent();
    }
}
