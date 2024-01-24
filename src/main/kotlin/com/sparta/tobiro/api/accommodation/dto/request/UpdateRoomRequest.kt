package com.sparta.tobiro.api.accommodation.dto.request

class UpdateRoomRequest(
    val name: String,
//    val roomPics: List<MultipartFile>
    val price: Int,
    val description: String,
    val maxOccupancy: Int,
)