package com.zeroboase.reservation.domain.common.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * 페이징 응답
 *
 * @param <T> 반환할 객체 타입
 */
@Builder
@Getter
public class PageResponse<T> {

    /**
     * 객체 목록
     */
    private final List<T> content;

    /**
     * 페이지 번호
     */
    private final int pageNumber;

    /**
     * 페이지 크기
     */
    private final int pageSize;

    /**
     * 전체 페이지 수
     */
    private final int totalPages;

    /**
     * 현재 페이지의 객체 수
     */
    private final int numberOfElements;

    /**
     * 전체 객체 수
     */
    private final long totalElements;

    /**
     * 첫 페이지 여부
     */
    private final boolean first;

    /**
     * 마지막 페이지 여부
     */
    private final boolean last;

    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
            .content(page.getContent())
            .pageNumber(page.getNumber() + 1)
            .pageSize(page.getSize())
            .totalPages(page.getTotalPages())
            .numberOfElements(page.getNumberOfElements())
            .totalElements(page.getTotalElements())
            .first(page.isFirst())
            .last(page.isLast())
            .build();
    }
}
