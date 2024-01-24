package com.sparta.tobiro.domain.reservation.dto

data class UpdateReviewRequest (
        val content: String,
        val rating: Int
)