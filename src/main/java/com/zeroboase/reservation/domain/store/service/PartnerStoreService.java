package com.zeroboase.reservation.domain.store.service;

import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.store.dto.CreateStore;
import com.zeroboase.reservation.domain.store.dto.UpdateStore;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreInfoDto;
import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;

public interface PartnerStoreService {

    PartnerStoreInfoDto createStore(CreateStore.Request request);

    PartnerStoreInfoDto getStoreInfo(Long id);

    // TODO: 페이징 처리 여부
    PageResponse<PartnerStoreDto> getStoreList(PageQuery query);

    UpdateStore.Response updateStore(Long id, UpdateStore.Request request);

    void deleteStore(Long id);

    boolean checkAccessReadStore(Long id, Member username);

    boolean checkAccessUpdateStore(Long id, Member username);

    boolean checkAccessDeleteStore(Long id, Member username);
}
