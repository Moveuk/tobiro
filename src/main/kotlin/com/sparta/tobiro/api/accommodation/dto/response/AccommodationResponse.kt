package com.sparta.tobiro.api.accommodation.dto.response

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.model.Category
import com.sparta.tobiro.domain.accommodation.model.Room

data class AccommodationResponse(
    val id: Long,
    val category: Category,
    val accommodationPicUrls: MutableList<String>,
    val rooms: List<RoomDetailResponse>,
    val address: String,
    val tlno: String,
    val name: String,
    val description: String
) {
    companion object {
        fun from(accommodation: Accommodation, rooms: List<Room>): AccommodationResponse {
            return AccommodationResponse(
                id = accommodation.id!!,
                category = accommodation.category,
                accommodationPicUrls = accommodation.accommodationPicUrls,
                rooms = rooms.map { RoomDetailResponse.from(it) },
                address = accommodation.address,
                tlno = accommodation.tlno,
                name = accommodation.name,
                description = accommodation.description
            )
        }
    }
}
