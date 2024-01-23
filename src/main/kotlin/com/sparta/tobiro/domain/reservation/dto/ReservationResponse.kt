package com.sparta.tobiro.domain.reservation.dto

import java.time.LocalDateTime

data class ReservationResponse (
        val reservationId : Long,
        val name : String,
        //val accommodationInfo : AccommodationResponse,
        //val roomInfo: RoomResponse,
        val checkinDate : LocalDateTime,
        val checkoutDate: LocalDateTime,
        val occupancy: Int,
        val fullCharge: Int,
        val createdAt: LocalDateTime,
        val modifiedAt: LocalDateTime
)