package com.sparta.tobiro.domain.reservation.model

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

        @Column(name = "created_date", nullable = false)
        var createdDate: LocalDateTime = LocalDateTime.now(),

        @Column(name = "modified_date", nullable = false)
        var modifiedDate: LocalDateTime = LocalDateTime.now(),

        //@Column(name = "member_id", nullable = false)
        //var member: Member,

        //@Column(name = "room_info_id", nullable = false)
        //var roomInfo: RoomInfo,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}