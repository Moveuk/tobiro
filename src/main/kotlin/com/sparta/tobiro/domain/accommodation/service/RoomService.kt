package com.sparta.tobiro.domain.accommodation.service

import com.sparta.tobiro.api.accommodation.dto.request.CreateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.request.UpdateRoomRequest
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
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val accommodationRepository: AccommodationRepository
) {
    fun getRooms(accommodationId: Long, pageable: Pageable, authentication: Authentication?): Page<RoomResponse> {
        //TODO: ADMIN의 경우 생각해서 인증 인가 기능 추가 후 authenication에서 꺼내 사용함.
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

    fun getRoom(roomId: Long): RoomResponse {
        //TODO: ADMIN의 경우 생각해서 인증 인가 기능 추가 후 authenication에서 꺼내 사용함.
        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)
        return RoomResponse.from(findRoom)
    }

    @Transactional
    fun createRoom(accommodationId: Long, request: CreateRoomRequest): RoomResponse {
        val findAccommodation =
            accommodationRepository
                .findByIdOrNull(accommodationId) ?: throw ModelNotFoundException("Accommodation")

        return roomRepository.save(request.to(findAccommodation)).let {
            RoomResponse.from(it)
        }

    }

    @Transactional
    fun updateRoom(roomId: Long, request: UpdateRoomRequest): RoomResponse {
        //TODO: ADMIN의 경우 생각해서 인증 인가 기능 추가 후 authenication에서 꺼내 사용함.
        val userPrincipalId = 1L

        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)

        if (findRoom.accommodation.owner.id != userPrincipalId) throw UnauthorizedException("이 숙박업소 객실에 대한 권한이 없습니다.")

        findRoom.update(request)

        return RoomResponse.from(findRoom)
    }

    fun deleteRoom(roomId: Long): String {
        //TODO: ADMIN의 경우 생각해서 인증 인가 기능 추가 후 authenication에서 꺼내 사용함.
        val userPrincipalId = 1L

        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)

        if (findRoom.accommodation.owner.id != userPrincipalId) throw UnauthorizedException("이 숙박업소 객실에 대한 권한이 없습니다.")

        roomRepository.delete(findRoom)

        return "삭제 성공"
    }

}
