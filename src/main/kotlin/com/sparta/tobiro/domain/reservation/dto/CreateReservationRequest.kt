package com.sparta.tobiro.domain.reservation.dto

import com.sparta.tobiro.domain.accommodation.model.Room
import com.sparta.tobiro.domain.member.model.Member
import com.sparta.tobiro.domain.reservation.model.Reservation
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period

data class CreateReservationRequest(
    val roomId: Long,
    val checkinDate: LocalDate,
    val checkoutDate: LocalDate,
    val occupancy: Int
) {
    fun to(member: Member, room: Room): Reservation {
        val period = Period.between(checkinDate, checkoutDate)
        val days = period.days
        return Reservation(
            checkinDate = checkinDate.atTime(15,0),
            checkoutDate = checkoutDate.atTime(LocalTime.NOON),
            occupancy = occupancy,
            fullCharge = room.price * days,
            member = member,
            room = room
        )
    }
}