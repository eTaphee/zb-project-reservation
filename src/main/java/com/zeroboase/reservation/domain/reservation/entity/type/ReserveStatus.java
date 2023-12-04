package com.zeroboase.reservation.domain.reservation.entity.type;

/**
 * 예상 상태
 */
public enum ReserveStatus {
    /**
     * 대기
     */
    WAIT,
    /**
     * 승인
     */
    APPROVE,
    /**
     * 거절
     */
    REJECT,
    /**
     * 취소
     */
    CANCEL
}
