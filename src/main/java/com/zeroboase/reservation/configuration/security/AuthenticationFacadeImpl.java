package com.zeroboase.reservation.configuration.security;

import com.zeroboase.reservation.domain.Member;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public Member getAuthenticatedMember() {
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
