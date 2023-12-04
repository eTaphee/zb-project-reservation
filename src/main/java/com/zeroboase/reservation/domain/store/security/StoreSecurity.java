package com.zeroboase.reservation.domain.store.security;

import static com.zeroboase.reservation.exception.ErrorCode.ACCESS_DENIED;
import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;

import com.zeroboase.reservation.configuration.security.AuthenticationFacade;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.store.entity.Store;
import com.zeroboase.reservation.domain.store.repository.StoreRepository;
import com.zeroboase.reservation.exception.ReservationException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class StoreSecurity {

    private final StoreRepository storeRepository;
    private final AuthenticationFacade authenticationFacade;

    public boolean checkAccessAsPartner(Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));

        return checkAccess(store.getPartner());
    }

    private boolean checkAccess(Member member) {
        Member authenticatedMember = authenticationFacade.getAuthenticatedMember();

        if (!Objects.equals(authenticatedMember.getUsername(), member.getUsername())) {
            throw new ReservationException(ACCESS_DENIED);
        }

        return true;
    }
}
