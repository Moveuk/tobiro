package com.sparta.tobiro.api.reservation.controller

import com.sparta.tobiro.domain.reservation.dto.CreateReviewRequest
import com.sparta.tobiro.domain.reservation.dto.ReviewResponse
import com.sparta.tobiro.domain.reservation.dto.UpdateReviewRequest
import com.sparta.tobiro.domain.reservation.service.ReviewService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/accommodations/{accommodationId}/reviews")
@RestController
class ReviewController(
        private val reviewService: ReviewService
) {
    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping
    fun createReview(
            @Valid
            @RequestBody createReviewRequest: CreateReviewRequest)
            : ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.createReview(createReviewRequest))
    }

    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @PutMapping("/{reviewId}")
    fun updateReview(
            @PathVariable reviewId: Long,
            @Valid
            @RequestBody updateReviewRequest: UpdateReviewRequest)
            : ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.updateReview(reviewId, updateReviewRequest))
    }
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @DeleteMapping("{reviewId}")
    fun deleteReview(
            @PathVariable reviewId: Long) : ResponseEntity<Unit>{
            reviewService.deleteReview(reviewId)
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build()
    }

}