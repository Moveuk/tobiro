package com.sparta.tobiro.api.accommodation.dto.response

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.model.Category

data class AccommodationResponse(
    val id: Long,
    val category: Category,
//    val accommodation_pic_urls: String,
    val address: String,
    val tlno: String,
    val name: String,
    val description: String
) {
    companion object {
        fun from(accommodation: Accommodation): AccommodationResponse {
            return AccommodationResponse(
                id = accommodation.id!!,
                category = accommodation.category,
                address = accommodation.address,
                tlno = accommodation.tlno,
                name = accommodation.name,
                description = accommodation.description
            )
        }
    }
}
