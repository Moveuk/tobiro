package com.sparta.tobiro.domain.reservation.dto

data class ReviewResponse (
        val memberName: String,
        val content: String,
        val rating: Int
)