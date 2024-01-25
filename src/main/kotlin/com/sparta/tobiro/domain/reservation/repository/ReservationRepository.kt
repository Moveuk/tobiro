package com.sparta.tobiro.domain.reservation.repository

import com.sparta.tobiro.domain.accommodation.model.Room
import com.sparta.tobiro.domain.reservation.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ReservationRepository : JpaRepository<Reservation, Long>, CustomReservationRepository {

    fun existsByRoomAndCheckinDateBetween(room: Room, newBookCheckinDate: LocalDateTime, newBookCheckoutDate: LocalDateTime): Boolean
    fun existsByRoomAndCheckoutDateBetween(room: Room, newBookCheckinDate: LocalDateTime, newBookCheckoutDate: LocalDateTime): Boolean
}