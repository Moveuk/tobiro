package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.domain.reservation.dto.CreateReservationRequest
import com.sparta.tobiro.domain.reservation.dto.ReservationResponse

interface ReservationService {

    fun createReservation(request: CreateReservationRequest): ReservationResponse?


}