package com.sparta.tobiro.domain.accommodation.model

import com.sparta.tobiro.api.accommodation.dto.request.UpdateRoomRequest
import com.sparta.tobiro.global.StringMutableListConverter
import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "room")
class Room(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var accommodation: Accommodation,

    @Column(name = "room_pic_urls", nullable = false)
    @Convert(converter = StringMutableListConverter::class)
    var roomPicUrls: MutableList<String> = mutableListOf("https://imgur.com/a/tBAKHUn"),

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "price", nullable = false)
    var price: Int,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "max_occupancy", nullable = false)
    var maxOccupancy: Int
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun update(request: UpdateRoomRequest, uploadedImageStrings: MutableList<String>?) {
        name = request.name
        roomPicUrls = uploadedImageStrings ?: this.roomPicUrls
        price = request.price
        description = request.description
        maxOccupancy = request.maxOccupancy
    }
}
