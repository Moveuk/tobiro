package com.sparta.tobiro.api.accommodation.dto.request

import org.springframework.web.multipart.MultipartFile

class UpdateRoomRequest(
    val name: String,
    val price: Int,
    val description: String,
    val maxOccupancy: Int,
    val roomPics: MutableList<MultipartFile>,
) {
    fun isPicsEmpty(): Boolean {
        return roomPics?.get(0)?.originalFilename == ""
    }
}