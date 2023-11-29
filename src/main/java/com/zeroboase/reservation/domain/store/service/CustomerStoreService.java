package com.zeroboase.reservation.domain.store.service;

import com.zeroboase.reservation.domain.store.dto.model.CustomerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.CustomerStoreInfoDto;
import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;

public interface CustomerStoreService {

    PageResponse<CustomerStoreDto> getStoreList(
        PageQuery query,
        Double latitude,
        Double longitude);

    CustomerStoreInfoDto getStoreInfo(Long id);
}
