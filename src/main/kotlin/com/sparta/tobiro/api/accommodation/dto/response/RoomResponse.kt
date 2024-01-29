package com.sparta.tobiro.api.accommodation.dto.response

import com.sparta.tobiro.domain.accommodation.model.Room

data class RoomResponse(
    val id: Long,
    val accommodation: AccommodationDetailResponse,
    val roomPicUrls: MutableList<String>,
    val name: String,
    val price: Int,
    val description: String,
    val maxOccupancy: Int
) {
    companion object {
        fun from(room: Room): RoomResponse {
            return RoomResponse(
                id = room.id!!,
                accommodation = AccommodationDetailResponse.from(room.accommodation),
                roomPicUrls = room.roomPicUrls,
                name = room.name,
                price = room.price,
                description = room.description,
                maxOccupancy = room.maxOccupancy
            )
        }
    }
}
