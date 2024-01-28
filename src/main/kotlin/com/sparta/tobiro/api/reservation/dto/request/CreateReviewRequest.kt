package com.sparta.tobiro.api.reservation.dto.request

import com.sparta.tobiro.api.reservation.validation.ValidContent
import com.sparta.tobiro.api.reservation.validation.ValidRating

data class CreateReviewRequest (
        val reservationId: Long,

        //글자수 제한 시 size, 영어나 특수문자 pattern
        @field: ValidContent
        val content: String,

        @field: ValidRating
        val rating: Int
)