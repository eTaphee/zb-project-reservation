package com.zeroboase.reservation.service;

import com.zeroboase.reservation.dto.CustomerStoreDto;
import com.zeroboase.reservation.dto.CustomerStoreInfoDto;
import com.zeroboase.reservation.dto.request.PageQueryDto;
import com.zeroboase.reservation.dto.response.PageResponseDto;

public interface CustomerStoreService {

    PageResponseDto<CustomerStoreDto> getStoreList(
        PageQueryDto query,
        Double latitude,
        Double longitude);

    CustomerStoreInfoDto getStoreInfo(Long id);
}
