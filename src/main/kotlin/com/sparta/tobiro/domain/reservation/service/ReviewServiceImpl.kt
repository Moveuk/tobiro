package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.domain.reservation.dto.CreateReviewRequest
import com.sparta.tobiro.domain.reservation.dto.ReviewResponse
import com.sparta.tobiro.domain.reservation.dto.UpdateReviewRequest
import com.sparta.tobiro.domain.reservation.model.Reservation
import com.sparta.tobiro.domain.reservation.model.Review
import com.sparta.tobiro.domain.reservation.model.toResponse
import com.sparta.tobiro.domain.reservation.repository.ReservationRepository
import com.sparta.tobiro.domain.reservation.repository.ReviewRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.infra.security.MemberPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewServiceImpl(
        private val reviewRepository: ReviewRepository,
        private val reservationRepository: ReservationRepository
) : ReviewService {

    @Transactional
    override fun createReview(request: CreateReviewRequest): ReviewResponse {
        //TODO : 예약 하나 당 후기 1개만 가능, 이미 후기 존재하면 수정, 삭제만 가능하다고 띄우기
        //TODO : 투숙한 사람만 리뷰 작성 가능(login member id 가 reservation.member,id와 같아야한다 같지않으면 throw)
        val findReservation: Reservation = reservationRepository.findByIdOrNull(request.reservationId)
                ?: throw ModelNotFoundException("Reservation", request.reservationId)
        return reviewRepository.save(
                Review(
                        reservation = findReservation,
                        content = request.content,
                        rating = request.rating
                )).toResponse()
    }

    @Transactional
    override fun updateReview(reviewId: Long, request: UpdateReviewRequest): ReviewResponse {
        //TODO : 본인(인가받은 사람) 및 admin 만 수정 가능
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ModelNotFoundException("Review", reviewId)
        review.content = request.content
        review.rating = request.rating
        return reviewRepository.save(review).toResponse()
    }

    @Transactional
    override fun deleteReview(reviewId: Long){
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ModelNotFoundException("Review", reviewId)
        reviewRepository.delete(review)
    }
}
