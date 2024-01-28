package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.api.reservation.dto.request.CreateReviewRequest
import com.sparta.tobiro.api.reservation.dto.request.UpdateReviewRequest
import com.sparta.tobiro.api.reservation.dto.response.ReviewResponse

interface ReviewService {

    fun createReview(request: CreateReviewRequest): ReviewResponse

    fun updateReview(reviewId: Long, request: UpdateReviewRequest): ReviewResponse

    fun deleteReview(reviewId: Long)
}