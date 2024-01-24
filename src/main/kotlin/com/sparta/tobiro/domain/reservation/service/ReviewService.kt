package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.domain.reservation.dto.CreateReviewRequest
import com.sparta.tobiro.domain.reservation.dto.ReviewResponse
import com.sparta.tobiro.domain.reservation.dto.UpdateReviewRequest

interface ReviewService {

    fun createReview(request: CreateReviewRequest): ReviewResponse

    fun updateReview(reviewId: Long, request: UpdateReviewRequest): ReviewResponse

    fun deleteReview(reviewId: Long)
}