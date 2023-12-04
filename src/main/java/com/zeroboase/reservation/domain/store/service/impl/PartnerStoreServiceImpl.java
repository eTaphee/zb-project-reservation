package com.zeroboase.reservation.domain.store.service.impl;

import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;

import com.zeroboase.reservation.configuration.security.AuthenticationFacade;
import com.zeroboase.reservation.domain.common.dto.PageQuery;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.store.dto.CreateStore;
import com.zeroboase.reservation.domain.store.dto.UpdateStore;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreInfoDto;
import com.zeroboase.reservation.domain.store.entity.Store;
import com.zeroboase.reservation.domain.store.mapper.StoreMapper;
import com.zeroboase.reservation.domain.store.repository.StoreRepository;
import com.zeroboase.reservation.domain.store.service.PartnerStoreService;
import com.zeroboase.reservation.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 파트너 매장 서비스
 */
@Service(value = "partnerStoreService")
@RequiredArgsConstructor
public class PartnerStoreServiceImpl implements PartnerStoreService {

    private final AuthenticationFacade authenticationFacade;
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    /**
     * 매장 추가
     *
     * @param request 매장 추가 요청
     * @return 추가된 매장 정보
     */
    @Transactional
    @Override
    public PartnerStoreInfoDto createStore(CreateStore.Request request) {
        Store save = storeRepository.save(Store.builder()
            .name(request.name())
            .description(request.description())
            .tel(request.tel())
            .address(request.address())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .build());

        return storeMapper.map(save);
    }

    /**
     * 파트너 매장 상세 정보 조회
     *
     * @param id 매장 아이디
     * @return 파트너 매장 상세 정보
     */
    @Transactional(readOnly = true)
    @Override
    public PartnerStoreInfoDto getStoreInfo(Long id) {
        return storeRepository.findPartnerStoreInfoById(id)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));
    }

    /**
     * 파트너 매장 목록 페이징 조회
     *
     * @param query 페이징 요청
     * @return 페이징된 파트너 매장 목록
     */
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PartnerStoreDto> getStoreList(PageQuery query) {
        Member member = authenticationFacade.getAuthenticatedMember();
        return PageResponse.from(
            storeRepository.findAllDtoByPartnerId(query.toPageable(), member.getId()));
    }

    /**
     * 파트너 매장 수정
     *
     * @param id      수정 할 매장 아이디
     * @param request 매장 수정 요청
     */
    @Transactional
    @Override
    public UpdateStore.Response updateStore(Long id, UpdateStore.Request request) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));

        storeMapper.updateStoreFromDto(request, store);

        storeRepository.save(store);

        return storeMapper.mapToUpdateStoreResponse(store);
    }

    /**
     * 파트너 매장 삭제
     *
     * @param id 삭제 할 매장 아이디
     */
    @Transactional
    @Override
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
