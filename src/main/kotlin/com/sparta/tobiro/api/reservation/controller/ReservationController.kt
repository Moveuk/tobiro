package com.sparta.tobiro.api.reservation.controller

import com.sparta.tobiro.domain.reservation.dto.CreateReservationRequest
import com.sparta.tobiro.domain.reservation.dto.ReservationResponse
import com.sparta.tobiro.domain.reservation.service.ReservationService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping()
    fun getReservations(
        @PageableDefault(size = 5, sort = ["id"]) pageable: Pageable
    ): ResponseEntity<Page<ReservationResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getReservations(pageable))
    }

    @DeleteMapping("/{reservationId}")
    fun deleteReservation(@PathVariable reservationId: Long): ResponseEntity<Unit> {
        reservationService.deleteReservation(reservationId)
        return ResponseEntity.noContent().build()
    }
}