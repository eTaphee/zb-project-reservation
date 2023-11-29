package com.zeroboase.reservation.configuration.security;

import com.zeroboase.reservation.domain.member.entity.Member;

public interface AuthenticationFacade {

    Member getAuthenticatedMember();
}
