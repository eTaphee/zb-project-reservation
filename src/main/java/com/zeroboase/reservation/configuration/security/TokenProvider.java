package com.zeroboase.reservation.configuration.security;

import static com.zeroboase.reservation.exception.ErrorCode.JWT_ERROR;
import static com.zeroboase.reservation.exception.ErrorCode.TOKEN_EXPIRED;

import com.zeroboase.reservation.dto.TokenDto;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.service.MemberService;
import com.zeroboase.reservation.type.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    /**
     * 토큰 유효 시간 (1 시간 = 3600000 밀리초)
     */
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
    private static final String KEY_ROLES = "roles";
    private final MemberService memberService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    /**
     * JWT를 생성
     *
     * @param username 사용자 아이디
     * @param roles    사용자 역할
     * @return JWT
     */
    public TokenDto generateToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return new TokenDto(Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact());
    }

    /**
     * 토큰 유효성 검사
     *
     * @param token JWT
     * @return 성공 시 true, 실패 시 false
     */
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    /**
     * 인증 정보 반환
     * <p>
     * jwt의 sub 클래임으로 사용자 정보를 조회하여 인증 정보를 반환
     *
     * @param jwt
     * @return 인증 정보
     */
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = memberService.loadUserByUsername(getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    /**
     * JWT sub (subject) 클래임 값을 반환
     *
     * @param token JWT
     * @return 사용자 아이디
     */
    private String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * JWT를 클래임으로 반환
     *
     * @param token JWT
     * @return 클래임 목록
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new ReservationException(TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new ReservationException(JWT_ERROR);
        }
    }
}
