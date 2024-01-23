package com.sparta.tobiro.domain.reservation.dto

data class CreateReviewRequest (
        val reservationId: Long,
        val content: String,
        val rating: Int
)