package com.sparta.tobiro.domain.accommodation.repository

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.model.Room
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<Room, Long>, CustomRoomRepository {

    fun findAllByAccommodation(accommodation: Accommodation): List<Room>
}