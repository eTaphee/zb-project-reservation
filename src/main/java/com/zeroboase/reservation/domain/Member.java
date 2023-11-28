package com.zeroboase.reservation.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.zeroboase.reservation.type.Constant;
import com.zeroboase.reservation.type.Role;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 사용자 엔티티(고객, 파트너)
 * <p>
 * 고객, 파트너 정보가 동일하기 때문에 분리하지 않고 role로 구분
 */
@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {

    /**
     * 사용자 PK
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * 아이디
     */
    @Column(unique = true, nullable = false)
    private final String username;

    /**
     * 비밀번호
     */
    @Column(nullable = false)
    private final String password;

    /**
     * 역할
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private final List<Role> roles;

    /**
     * 사용자 등록일시
     */
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime registeredAt;

    @Builder
    public Member(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.requireNonNull(this.roles).stream()
            .map(role -> new SimpleGrantedAuthority(Constant.ROLE_PREFIX + role))
            .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
