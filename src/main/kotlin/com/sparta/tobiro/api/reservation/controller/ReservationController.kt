package com.sparta.tobiro.api.reservation.controller

import com.sparta.tobiro.domain.reservation.dto.CreateReservationRequest
import com.sparta.tobiro.domain.reservation.dto.ReservationResponse
import com.sparta.tobiro.domain.reservation.service.ReservationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/reservations")
@RestController
class ReservationController(
    private val reservationService: ReservationService
) {

    @PostMapping
    fun createReservation(
        @RequestBody createReservationRequest: CreateReservationRequest
    ): ResponseEntity<ReservationResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reservationService.createReservation(createReservationRequest))
    }

    @DeleteMapping("/{reservationId}")
    fun deleteReservation(@PathVariable reservationId: Long): ResponseEntity<Unit>{
        reservationService.deleteReservation(reservationId)
        return ResponseEntity.noContent().build()
    }
}