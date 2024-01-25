package com.sparta.tobiro.domain.accommodation.repository

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.member.model.Owner
import org.springframework.data.jpa.repository.JpaRepository

interface AccommodationRepository : JpaRepository<Accommodation, Long>, CustomRoomRepository {
    fun findByOwner(owner: Owner): Accommodation?
}