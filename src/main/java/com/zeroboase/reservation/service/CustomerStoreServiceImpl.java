package com.zeroboase.reservation.service;

import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;

import com.zeroboase.reservation.dto.CustomerStoreDto;
import com.zeroboase.reservation.dto.CustomerStoreInfoDto;
import com.zeroboase.reservation.dto.request.PageQueryDto;
import com.zeroboase.reservation.dto.response.PageResponseDto;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.repository.StoreRepository;
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
    public PageResponseDto<CustomerStoreDto> getStoreList(PageQueryDto query, Double latitude,
        Double longitude) {
        return PageResponseDto.from(
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
