package com.sparta.tobiro.domain.accommodation.service

import com.sparta.tobiro.api.accommodation.dto.request.CreateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.repository.AccommodationRepository
import com.sparta.tobiro.domain.accommodation.repository.RoomRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val accommodationRepository: AccommodationRepository
) {
    fun createRoom(accommodationId: Long, request: CreateRoomRequest): RoomResponse {
        val findAccommodation =
            accommodationRepository
                .findByIdOrNull(accommodationId) ?: throw ModelNotFoundException("Accommodation", null)

        return roomRepository.save(request.to(findAccommodation)).let {
            RoomResponse.from(it)
        }

    }

}
