package com.sparta.tobiro.domain.member.repository

import com.sparta.tobiro.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByEmail(email: String): Boolean
}