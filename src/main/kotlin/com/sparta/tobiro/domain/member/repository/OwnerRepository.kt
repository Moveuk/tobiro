package com.sparta.tobiro.domain.member.repository

import com.sparta.tobiro.domain.member.model.Owner
import org.springframework.data.jpa.repository.JpaRepository

interface OwnerRepository : JpaRepository<Owner, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email:String) : Owner?
}