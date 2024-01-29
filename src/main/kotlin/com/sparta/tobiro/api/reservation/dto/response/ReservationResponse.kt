package com.sparta.tobiro.api.reservation.dto.response

import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.reservation.model.Reservation
import java.time.LocalDateTime

data class ReservationResponse(
    val reservationId: Long,
    val room: RoomResponse,
    val checkinDate: LocalDateTime,
    val checkoutDate: LocalDateTime,
    val occupancy: Int,
    val fullCharge: Int,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    companion object {
        fun from(reservation: Reservation): ReservationResponse {
            return ReservationResponse(
                reservationId = reservation.id!!,
                room = RoomResponse.from(reservation.room),
                checkinDate = reservation.checkinDate,
                checkoutDate = reservation.checkoutDate,
                occupancy = reservation.occupancy,
                fullCharge = reservation.fullCharge,
                createdAt = reservation.createdAt,
                modifiedAt = reservation.modifiedAt
            )
        }
    }
}