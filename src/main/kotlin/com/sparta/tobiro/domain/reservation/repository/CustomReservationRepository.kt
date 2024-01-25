package com.sparta.tobiro.domain.reservation.repository

import com.sparta.tobiro.domain.reservation.model.Reservation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomReservationRepository {

    fun findAllByPageableAndMemberId(pageable: Pageable, memberId: Long): Page<Reservation>
}