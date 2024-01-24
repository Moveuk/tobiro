package com.sparta.tobiro.domain.reservation.model

import com.sparta.tobiro.domain.accommodation.model.Room
import com.sparta.tobiro.domain.member.model.Member
import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reservation")
class Reservation(
    @Column(name = "checkin_date", nullable = false)
    var checkinDate: LocalDateTime,

    @Column(name = "checkout_date", nullable = false)
    var checkoutDate: LocalDateTime,

    @Column(name = "occupancy", nullable = false)
    var occupancy: Int,

    @Column(name = "full_charge", nullable = false)
    var fullCharge: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    var room: Room,

    ) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}