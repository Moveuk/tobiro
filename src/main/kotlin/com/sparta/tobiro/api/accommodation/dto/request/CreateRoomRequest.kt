package com.sparta.tobiro.api.accommodation.dto.request

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.model.Room

data class CreateRoomRequest(
    val name: String,
    val price: Int,
    val description: String,
    val maxOccupancy: Int,
) {
    fun to(accommodation: Accommodation): Room {
        return Room(
            accommodation = accommodation,
            name = name,
            price = price,
            description = description,
            maxOccupancy = maxOccupancy
        )
    }
}
