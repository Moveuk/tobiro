package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.domain.reservation.dto.CreateReviewRequest
import com.sparta.tobiro.domain.reservation.dto.ReviewResponse
import com.sparta.tobiro.domain.reservation.model.Reservation
import com.sparta.tobiro.domain.reservation.model.Review
import com.sparta.tobiro.domain.reservation.model.toResponse
import com.sparta.tobiro.domain.reservation.repository.ReservationRepository
import com.sparta.tobiro.domain.reservation.repository.ReviewRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewServiceImpl(
        private val reviewRepository: ReviewRepository,
        private val reservationRepository: ReservationRepository
) : ReviewService {

    @Transactional
    override fun createReview(createReviewRequest: CreateReviewRequest): ReviewResponse {
        //TODO : 투숙한 사람만 리뷰 작성 가능(login member id 가 reservation.member,id와 같아야한다 같지않으면 throw)
        val findReservation: Reservation = reservationRepository.findByIdOrNull(createReviewRequest.reservationId)
                ?: throw ModelNotFoundException("Reservation", createReviewRequest.reservationId)

        return reviewRepository.save(
                Review(
                        reservation = findReservation,
                        content = createReviewRequest.content,
                        rating = createReviewRequest.rating
                )).toResponse()
    }
}
