package com.sparta.tobiro.domain.reservation.repository

import com.sparta.tobiro.domain.reservation.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository: JpaRepository<Reservation, Long> {
}