package com.sparta.tobiro.domain.reservation.repository

import com.sparta.tobiro.domain.reservation.model.Reservation
import com.sparta.tobiro.domain.reservation.model.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository: JpaRepository<Review, Long> {
    fun findReviewByReservation(reservation: Reservation): Review?
}