package com.sparta.tobiro.domain.accommodation.service

import com.sparta.tobiro.api.accommodation.dto.request.CreateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.request.UpdateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.repository.AccommodationRepository
import com.sparta.tobiro.domain.accommodation.repository.RoomRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.global.exception.UnauthorizedException
import com.sparta.tobiro.infra.security.UserPrincipal
import org.apache.coyote.BadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val accommodationRepository: AccommodationRepository
) {
    fun getRooms(accommodationId: Long, pageable: Pageable, userprincipal: UserPrincipal): Page<RoomResponse> {
        val findAccommodation =
            accommodationRepository
                .findByIdOrNull(accommodationId) ?: throw ModelNotFoundException("Accommodation", accommodationId)

        if (findAccommodation.owner.id != userprincipal.id) throw UnauthorizedException("이 숙박업소에 대한 권한이 없습니다.")

        return roomRepository.findAllByPageableAndAccommodationId(pageable, accommodationId).map {
            RoomResponse.from(it)
        }.let {
            if (it.content.size == 0) throw BadRequestException("객실을 먼저 추가해주세요.")
            it
        }
    }

    fun getRoom(roomId: Long, userPrincipal: UserPrincipal): RoomResponse {
        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)
        if (findRoom.accommodation.owner.id != userPrincipal.id) throw UnauthorizedException("이 숙박업소에 대한 권한이 없습니다.")
        return RoomResponse.from(findRoom)
    }

    @Transactional
    fun createRoom(accommodationId: Long, request: CreateRoomRequest, userPrincipal: UserPrincipal): RoomResponse {
        val findAccommodation =
            accommodationRepository
                .findByIdOrNull(accommodationId) ?: throw ModelNotFoundException("Accommodation")
        if (findAccommodation.owner.id != userPrincipal.id) throw UnauthorizedException("이 숙박업소에 대한 권한이 없습니다.")
        return roomRepository.save(request.to(findAccommodation)).let {
            RoomResponse.from(it)
        }

    }

    @Transactional
    fun updateRoom(roomId: Long, request: UpdateRoomRequest, userPrincipal: UserPrincipal): RoomResponse {
        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)
        if (findRoom.accommodation.owner.id != userPrincipal.id) throw UnauthorizedException("이 숙박업소 객실에 대한 권한이 없습니다.")
        findRoom.update(request)
        return RoomResponse.from(findRoom)
    }

    @Transactional
    fun deleteRoom(roomId: Long, userPrincipal: UserPrincipal) {
        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)
        if (findRoom.accommodation.owner.id != userPrincipal.id) throw UnauthorizedException("이 숙박업소 객실에 대한 권한이 없습니다.")
        roomRepository.delete(findRoom)
    }

}
