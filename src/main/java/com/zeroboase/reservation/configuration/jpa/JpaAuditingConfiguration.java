package com.zeroboase.reservation.configuration.jpa;

import com.zeroboase.reservation.domain.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<Member> auditorProvider() {
        return new JpaAuditorAware();
    }
}
