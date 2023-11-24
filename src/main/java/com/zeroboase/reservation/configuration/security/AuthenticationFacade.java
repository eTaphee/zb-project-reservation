package com.zeroboase.reservation.configuration.security;

import com.zeroboase.reservation.domain.Member;

public interface AuthenticationFacade {

    Member getAuthenticatedMember();
}
