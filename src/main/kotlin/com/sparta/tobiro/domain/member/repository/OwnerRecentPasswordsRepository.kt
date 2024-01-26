package com.sparta.tobiro.domain.member.repository

import com.sparta.tobiro.domain.member.model.Owner
import com.sparta.tobiro.domain.member.model.OwnerRecentPasswords
import org.springframework.data.jpa.repository.JpaRepository

interface OwnerRecentPasswordsRepository : JpaRepository<OwnerRecentPasswords, Long> {
    fun findTop3ByOwnerOrderByIdDesc(owner: Owner): List<OwnerRecentPasswords>
}