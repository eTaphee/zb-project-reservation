package com.zeroboase.reservation.dto.request;

/**
 * 페이징 결과 요청 쿼리
 *
 * @param pageNumber 페이지 번호(1부터 시작)
 * @param pageSize   페이지 크기
 */
public record PageQueryDto(int pageNumber, int pageSize) {

    /**
     * Pageable을 사용하기 위해 pageNumber를 -1 처리
     * @return 요청에서 -1 된 페이지 번호(최소 0)
     */
    @Override
    public int pageNumber() {
        if (this.pageNumber > 0) {
            return this.pageNumber - 1;
        }

        return 0;
    }
}
