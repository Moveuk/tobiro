package com.sparta.tobiro.api.reservation.dto.request

import com.sparta.tobiro.api.reservation.validation.ValidContent
import com.sparta.tobiro.api.reservation.validation.ValidRating

data class UpdateReviewRequest (

        @field: ValidContent
        val content: String,

        @field: ValidRating
        val rating: Int
)