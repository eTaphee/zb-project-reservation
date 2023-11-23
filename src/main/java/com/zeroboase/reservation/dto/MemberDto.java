package com.zeroboase.reservation.dto;

import com.zeroboase.reservation.domain.Member;
import com.zeroboase.reservation.type.Role;
import java.util.List;
import lombok.Builder;

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