package com.sparta.tobiro.domain.member.repository

import com.sparta.tobiro.domain.member.model.Member
import com.sparta.tobiro.domain.member.model.MemberRecentPasswords
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRecentPasswordsRepository : JpaRepository<MemberRecentPasswords, Long> {
    fun findTop3ByMemberOrderByIdDesc(member: Member): List<MemberRecentPasswords>
}