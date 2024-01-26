package com.sparta.tobiro.domain.reservation.dto

data class ReviewResponse (
        val reviewId: Long,
        val memberName: String,
        val content: String,
        val rating: Int
)