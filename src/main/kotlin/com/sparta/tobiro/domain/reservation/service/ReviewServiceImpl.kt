package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.domain.reservation.dto.CreateReviewRequest
import com.sparta.tobiro.domain.reservation.dto.ReviewResponse
import com.sparta.tobiro.domain.reservation.dto.UpdateReviewRequest
import com.sparta.tobiro.domain.reservation.model.Reservation
import com.sparta.tobiro.domain.reservation.model.Review
import com.sparta.tobiro.domain.reservation.model.toResponse
import com.sparta.tobiro.domain.reservation.repository.ReservationRepository
import com.sparta.tobiro.domain.reservation.repository.ReviewRepository
import com.sparta.tobiro.global.exception.InvalidCredentialException
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.infra.security.UserPrincipal
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
        val findReservation: Reservation = reservationRepository.findByIdOrNull(request.reservationId)
                ?: throw ModelNotFoundException("Reservation", request.reservationId)

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        if (userPrincipal.id != findReservation.member.id) {throw InvalidCredentialException()}

        reviewRepository.findReviewByReservation(findReservation)?.let {
            if (it.reservation.member.id == userPrincipal.id) throw IllegalStateException("이미 후기가 존재합니다. 존재하는 후기의 수정, 삭제만 가능합니다.")
        }

        return reviewRepository.save(
                Review(
                        reservation = findReservation,
                        content = request.content,
                        rating = request.rating
                )).toResponse()
    }

    @Transactional
    override fun updateReview(reviewId: Long, request: UpdateReviewRequest): ReviewResponse {
        //TODO : admin도 수정 가능하게
        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ModelNotFoundException("Review", reviewId)
        if (review.reservation.member.id != userPrincipal.id
                || userPrincipal.authorities.first().authority != "ROLE_MEMBER")
                 {throw InvalidCredentialException()}
        review.content = request.content
        review.rating = request.rating
        return reviewRepository.save(review).toResponse()
    }

    @Transactional
    override fun deleteReview(reviewId: Long){
        //TODO : admin도 삭제 가능하게
        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ModelNotFoundException("Review", reviewId)
        if (review.reservation.member.id != userPrincipal.id
                || userPrincipal.authorities.first().authority != "ROLE_MEMBER")
                {throw InvalidCredentialException()}
        reviewRepository.delete(review)
    }
}
