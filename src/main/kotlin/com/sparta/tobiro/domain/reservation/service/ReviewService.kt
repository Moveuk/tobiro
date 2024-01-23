package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.domain.reservation.dto.CreateReviewRequest
import com.sparta.tobiro.domain.reservation.dto.ReviewResponse

interface ReviewService {

    fun createReview(createReviewRequest: CreateReviewRequest) : ReviewResponse
}