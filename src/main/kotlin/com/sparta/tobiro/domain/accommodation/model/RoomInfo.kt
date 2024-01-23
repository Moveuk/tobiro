package com.sparta.tobiro.domain.accommodation.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="room_info")
class RoomInfo(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var accommodationInfo: AccommodationInfo,

    @Column(name = "room_pic_urls", nullable = false)
    var roomPicUrls: String = "https://imgur.com/a/tBAKHUn",

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "price", nullable = false)
    var price: Int,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "max_occupancy", nullable = false)
    var maxOccupancy: Int,

    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modified_date", nullable = false)
    var modifiedDate: LocalDateTime = LocalDateTime.now(),
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
