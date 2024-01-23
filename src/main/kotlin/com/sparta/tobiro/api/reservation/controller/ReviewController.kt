package com.sparta.tobiro.api.reservation.controller

import com.sparta.tobiro.domain.reservation.dto.CreateReviewRequest
import com.sparta.tobiro.domain.reservation.dto.ReviewResponse
import com.sparta.tobiro.domain.reservation.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/accommodations/{accommodationId}/reviews")
@RestController
class ReviewController (
        private val reviewService: ReviewService
) {
    @PostMapping
    fun createReview(
            @RequestBody createReviewRequest: CreateReviewRequest)
    : ResponseEntity<ReviewResponse>{
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.createReview(createReviewRequest))
    }


}