package com.zeroboase.reservation.domain.store.service.impl;

import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;

import com.zeroboase.reservation.domain.store.dto.model.CustomerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.CustomerStoreInfoDto;
import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.domain.store.repository.StoreRepository;
import com.zeroboase.reservation.domain.store.service.CustomerStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 고객 매장 서비스
 */
@Service(value = "customerStoreService")
@RequiredArgsConstructor
public class CustomerStoreServiceImpl implements CustomerStoreService {

    private final StoreRepository storeRepository;

    /**
     * 고객 매장 목록 페이징 조회
     *
     * @param query     페이징 요청
     * @param latitude  현 위치 위도
     * @param longitude 현 위치 경도
     * @return 페이징된 매장 목록
     */
    @Override
    public PageResponse<CustomerStoreDto> getStoreList(PageQuery query, Double latitude,
        Double longitude) {
        return PageResponse.from(
            storeRepository.findAllCustomerStoreDto(query.toPageable(),
                latitude,
                longitude)
        );
    }

    /**
     * 고객 매장 상세 정보 조회
     *
     * @param id 매장 아이디
     * @return 매장 상세 정보
     */
    @Override
    public CustomerStoreInfoDto getStoreInfo(Long id) {
        return storeRepository.findCustomStoreInfoById(id)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));
    }
}
