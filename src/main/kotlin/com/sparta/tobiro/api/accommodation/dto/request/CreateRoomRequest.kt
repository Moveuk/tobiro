package com.sparta.tobiro.api.accommodation.dto.request

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.model.Room
import org.springframework.web.multipart.MultipartFile

data class CreateRoomRequest(
    val name: String,
    val price: Int,
    val description: String,
    val maxOccupancy: Int,
    val roomPics: MutableList<MultipartFile>?,
) {
    fun isPicsEmpty(): Boolean {
        return roomPics?.get(0)?.originalFilename == ""
    }
    fun to(accommodation: Accommodation, uploadedImageStrings: MutableList<String>?): Room {
        return Room(
            accommodation = accommodation,
            name = name,
            price = price,
            description = description,
            maxOccupancy = maxOccupancy,
            roomPicUrls = uploadedImageStrings ?: mutableListOf("https://imgur.com/a/tBAKHUn"),
        )
    }
}
