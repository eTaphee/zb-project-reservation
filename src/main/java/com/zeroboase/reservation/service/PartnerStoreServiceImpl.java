package com.zeroboase.reservation.service;

import static com.zeroboase.reservation.exception.ErrorCode.DELETE_STORE_FORBIDDEN;
import static com.zeroboase.reservation.exception.ErrorCode.READ_STORE_FORBIDDEN;
import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;
import static com.zeroboase.reservation.exception.ErrorCode.UPDATE_STORE_FORBIDDEN;

import com.zeroboase.reservation.configuration.security.AuthenticationFacade;
import com.zeroboase.reservation.domain.Member;
import com.zeroboase.reservation.domain.Store;
import com.zeroboase.reservation.dto.PartnerStoreDto;
import com.zeroboase.reservation.dto.PartnerStoreInfoDto;
import com.zeroboase.reservation.dto.request.partner.CreateStoreRequestDto;
import com.zeroboase.reservation.dto.request.PageQueryDto;
import com.zeroboase.reservation.dto.request.partner.UpdateStoreRequestDto;
import com.zeroboase.reservation.dto.response.PageResponseDto;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.mapper.StoreMapper;
import com.zeroboase.reservation.repository.StoreRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PartnerStoreDto createStore(CreateStoreRequestDto request) {
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
    public PageResponseDto<PartnerStoreDto> getStoreList(PageQueryDto query) {
        Member member = authenticationFacade.getAuthenticatedMember();
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize());
        return PageResponseDto.from(
            storeRepository.findAllDtoByPartnerId(pageable, member.getId()));
    }

    /**
     * 파트너 매장 수정
     *
     * @param id      수정 할 매장 아이디
     * @param request 매장 수정 요청
     */
    @Transactional
    @Override
    public PartnerStoreDto updateStore(Long id, UpdateStoreRequestDto request) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));

        storeMapper.updateStoreFromDto(request, store);

        storeRepository.save(store);

        return storeMapper.map(store);
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

    /**
     * 매장을 등록한 파트너인지 확인
     *
     * @param id     매장 아이디
     * @param member 로그인 사용자
     * @return 등록한 파트너이면 true, 아니면 false
     */
    private boolean checkPermission(Long id, Member member) {
        String partnerUsername = storeRepository.findPartnerUsernameById(id)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));
        return Objects.equals(member.getUsername(), partnerUsername);
    }

    /**
     * 매장 읽기 권한 확인
     *
     * @param id     매장 아이디
     * @param member 로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessReadStore(Long id, Member member) {
        if (!checkPermission(id, member)) {
            throw new ReservationException(READ_STORE_FORBIDDEN);
        }
        return true;
    }

    /**
     * 매장 수정 권한 확인
     *
     * @param id     매장 아이디
     * @param member 로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessUpdateStore(Long id, Member member) {
        if (!checkPermission(id, member)) {
            throw new ReservationException(UPDATE_STORE_FORBIDDEN);
        }
        return true;
    }

    /**
     * 매장 삭제 권한 확인
     *
     * @param id     매장 아이디
     * @param member 로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessDeleteStore(Long id, Member member) {
        if (!checkPermission(id, member)) {
            throw new ReservationException(DELETE_STORE_FORBIDDEN);
        }
        return true;
    }
}
