package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.domain.reservation.dto.CreateReservationRequest
import com.sparta.tobiro.domain.reservation.dto.ReservationResponse
import org.springframework.stereotype.Service

@Service
class ReservationServiceImpl: ReservationService {
    override fun createReservation(request: CreateReservationRequest): ReservationResponse? {
        //예약하려는 객실 남아있는지 확인
        return null
//        return reservationRepository.save(
//                Reservation(
//
//
//                )
//        ).toResponse()
    }
}