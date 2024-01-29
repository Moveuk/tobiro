package com.sparta.tobiro.api.reservation.dto.response

data class ReviewResponse (
        val reviewId: Long,
        val memberName: String,
        val content: String,
        val rating: Int
)