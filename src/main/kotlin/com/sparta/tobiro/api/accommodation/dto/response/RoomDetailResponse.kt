package com.sparta.tobiro.api.accommodation.dto.response

import com.sparta.tobiro.domain.accommodation.model.Room

data class RoomDetailResponse(
    val id: Long,
    val roomPicUrls: MutableList<String>,
    val name: String,
    val price: Int,
    val description: String,
    val maxOccupancy: Int
) {
    companion object {
        fun from(room: Room): RoomDetailResponse {
            return RoomDetailResponse(
                id = room.id!!,
                roomPicUrls = room.roomPicUrls,
                name = room.name,
                price = room.price,
                description = room.description,
                maxOccupancy = room.maxOccupancy
            )
        }
    }
}
