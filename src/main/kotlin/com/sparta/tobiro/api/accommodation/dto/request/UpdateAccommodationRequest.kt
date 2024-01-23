package com.sparta.tobiro.api.accommodation.dto.request

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.model.Category
import com.sparta.tobiro.domain.member.model.Owner

data class UpdateAccommodationRequest(
    val category: Category,
//    val accommodation_pics: List<MultipartFile>,
    val address: String,
    val tlno: String,
    val name: String,
    val description: String
) {
    fun to(owner: Owner): Accommodation {
        return Accommodation(
            owner = owner,
            category = category,
            address = address,
            tlno = tlno,
            name = name,
            description = description
        )
    }
}
