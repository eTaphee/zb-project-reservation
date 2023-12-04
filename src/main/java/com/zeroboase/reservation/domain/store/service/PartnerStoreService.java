package com.zeroboase.reservation.domain.store.service;

import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.store.dto.CreateStore;
import com.zeroboase.reservation.domain.store.dto.UpdateStore;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreInfoDto;

public interface PartnerStoreService {

    PartnerStoreInfoDto createStore(CreateStore.Request request);

    PartnerStoreInfoDto getStoreInfo(Long id);

    PageResponse<PartnerStoreDto> getStoreList(PageQuery query);

    UpdateStore.Response updateStore(Long id, UpdateStore.Request request);

    void deleteStore(Long id);
}
