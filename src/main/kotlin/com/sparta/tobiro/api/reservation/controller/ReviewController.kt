package com.sparta.tobiro.api.reservation.controller

import com.sparta.tobiro.domain.reservation.dto.CreateReviewRequest
import com.sparta.tobiro.domain.reservation.dto.ReviewResponse
import com.sparta.tobiro.domain.reservation.dto.UpdateReviewRequest
import com.sparta.tobiro.domain.reservation.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/accommodations/{accommodationId}/reviews")
@RestController
class ReviewController(
        private val reviewService: ReviewService
) {
    @PostMapping
    fun createReview(
            @RequestBody createReviewRequest: CreateReviewRequest)
            : ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.createReview(createReviewRequest))
    }

    @PutMapping("/{reviewId}")
    fun updateReview(
            @PathVariable reviewId: Long,
            @RequestBody updateReviewRequest: UpdateReviewRequest)
            : ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.updateReview(reviewId, updateReviewRequest))
    }

}