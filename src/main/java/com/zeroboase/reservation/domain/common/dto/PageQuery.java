package com.zeroboase.reservation.domain.common.dto;

import java.util.Objects;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

/**
 * 페이징 요청
 */
@Data
public class PageQuery {

    /**
     * 페이지 번호(1부터 시작)
     */
    private Integer pageNumber;

    /**
     * 페이지 크기
     */
    private Integer pageSize;

    /**
     * 정렬 기준 데이터
     */
    private String criteria;

    /**
     * 정렬 순서
     */
    private Direction order;

    public PageQuery() {
        pageNumber = 1;
        pageSize = 10;
    }

    /**
     * Pageable을 사용하기 위해 pageNumber를 -1 처리
     *
     * @return 요청에서 -1 된 페이지 번호(최소 0)
     */
    public Integer getPageNumber() {
        if (this.pageNumber > 0) {
            return this.pageNumber - 1;
        }

        return 0;
    }

    public Pageable toPageable() {
        if (Objects.isNull(criteria) || Objects.isNull(order)) {
            return PageRequest.of(getPageNumber(), getPageSize());
        }

        return PageRequest.of(getPageNumber(), getPageSize(), getOrder(), getCriteria());
    }
}
