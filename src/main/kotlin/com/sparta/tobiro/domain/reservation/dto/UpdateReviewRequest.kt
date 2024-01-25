package com.sparta.tobiro.domain.reservation.dto

import com.sparta.tobiro.domain.reservation.validation.ValidContent
import com.sparta.tobiro.domain.reservation.validation.ValidRating

data class UpdateReviewRequest (

        @field: ValidContent
        val content: String,

        @field: ValidRating
        val rating: Int
)