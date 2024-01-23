package com.sparta.tobiro.domain.reservation.model

import com.sparta.tobiro.domain.reservation.dto.ReviewResponse
import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "review")
class Review(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "reservation_id", nullable = false)
        var reservation: Reservation,

        @Column(name = "content", nullable = false)
        var content: String,

        @Column(name = "rating", nullable = false)
        var rating: Int,

        ) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Review.toResponse(): ReviewResponse {
    return ReviewResponse(
            name = reservation.member.name,
            content = content,
            rating = rating
    )
}