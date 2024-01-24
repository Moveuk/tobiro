package com.sparta.tobiro.domain.accommodation.service

import com.sparta.tobiro.api.accommodation.dto.request.CreateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.repository.AccommodationRepository
import com.sparta.tobiro.domain.accommodation.repository.RoomRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.global.exception.UnauthorizedException
import org.apache.coyote.BadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val accommodationRepository: AccommodationRepository
) {
    fun getRooms(accommodationId: Long, pageable: Pageable, authentication: Authentication?): Page<RoomResponse> {
        //TODO: 인증 인가 후 authenication에서 꺼내 사용함.
        val userPrincipalId = 1L
        val findAccommodation =
            accommodationRepository
                .findByIdOrNull(accommodationId) ?: throw ModelNotFoundException("Accommodation", accommodationId)

        if (findAccommodation.owner.id != userPrincipalId) throw UnauthorizedException("이 숙박업소에 대한 권한이 없습니다.")

        return roomRepository.findAllByPageableAndAccommodationId(pageable, accommodationId).map {
            RoomResponse.from(it)
        }.let {
            if (it.content.size == 0) throw BadRequestException("객실을 먼저 추가해주세요.")
            it
        }
    }

    fun createRoom(accommodationId: Long, request: CreateRoomRequest): RoomResponse {
        val findAccommodation =
            accommodationRepository
                .findByIdOrNull(accommodationId) ?: throw ModelNotFoundException("Accommodation")

        return roomRepository.save(request.to(findAccommodation)).let {
            RoomResponse.from(it)
        }

    }

}
