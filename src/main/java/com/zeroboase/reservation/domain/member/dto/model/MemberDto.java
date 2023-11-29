package com.zeroboase.reservation.domain.member.dto.model;

import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.member.entity.type.Role;
import java.util.List;
import lombok.Builder;

/**
 * 회원 정보
 *
 * @param username 아이디
 * @param roles    역할
 */
public record MemberDto(String username, List<Role> roles) {

    @Builder
    public MemberDto {
    }

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
            .username(member.getUsername())
            .roles(member.getRoles())
            .build();
    }
}
