package com.sparta.tobiro.api.accommodation.dto.response

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.model.Category

data class AccommodationDetailResponse(
    val id: Long,
    val category: Category,
    val accommodationPicUrls: String,
    val address: String,
    val tlno: String,
    val name: String,
    val description: String
) {
    companion object {
        fun from(accommodation: Accommodation): AccommodationDetailResponse {
            return AccommodationDetailResponse(
                id = accommodation.id!!,
                category = accommodation.category,
                accommodationPicUrls = accommodation.accommodationPicUrls,
                address = accommodation.address,
                tlno = accommodation.tlno,
                name = accommodation.name,
                description = accommodation.description
            )
        }
    }
}
