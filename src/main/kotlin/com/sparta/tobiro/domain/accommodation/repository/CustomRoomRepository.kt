package com.sparta.tobiro.domain.accommodation.repository

import com.sparta.tobiro.domain.accommodation.model.Room
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface CustomRoomRepository {
    fun findAllByPageableAndAccommodationId(pageable: Pageable, accommodationId: Long): Page<Room>
    fun searchAccommodations(pageable: Pageable, query: String?, checkinDate: LocalDate, checkoutDate: LocalDate): Page<Room>
}