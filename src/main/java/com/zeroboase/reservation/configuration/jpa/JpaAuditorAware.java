package com.zeroboase.reservation.configuration.jpa;

import com.zeroboase.reservation.domain.Member;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 엔티티 @CreatedBy, @LastModifiedBy 사용하기 위한 감사(auditing) 인터페이스 구현체
 */
public class JpaAuditorAware implements AuditorAware<Member> {

    @Override
    public Optional<Member> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
            || !authentication.isAuthenticated()
            || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.ofNullable((Member) authentication.getPrincipal());
    }
}
