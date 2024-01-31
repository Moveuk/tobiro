package com.sparta.tobiro.domain.accommodation.service

import com.sparta.tobiro.api.accommodation.dto.request.CreateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.request.UpdateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.repository.AccommodationRepository
import com.sparta.tobiro.domain.accommodation.repository.RoomRepository
import com.sparta.tobiro.domain.member.model.Owner
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.global.exception.UnauthorizedException
import com.sparta.tobiro.infra.aws.S3Service
import com.sparta.tobiro.infra.security.UserPrincipal
import kotlinx.coroutines.runBlocking
import org.apache.coyote.BadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val accommodationRepository: AccommodationRepository,
    private val s3Service: S3Service
) {
    fun getRooms(accommodationId: Long, pageable: Pageable, userPrincipal: UserPrincipal): Page<RoomResponse> {
        val findAccommodation =
            accommodationRepository
                .findByIdOrNull(accommodationId) ?: throw ModelNotFoundException("Accommodation", accommodationId)

        checkBetweenOwnerAndLoginUser(findAccommodation.owner, userPrincipal)

        return roomRepository.findAllByPageableAndAccommodationId(pageable, accommodationId).map {
            RoomResponse.from(it)
        }.let {
            if (it.content.size == 0) throw BadRequestException("객실을 먼저 추가해주세요.")
            it
        }
    }

    fun getRoom(roomId: Long, userPrincipal: UserPrincipal): RoomResponse {
        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)
        checkBetweenOwnerAndLoginUser(findRoom.accommodation.owner, userPrincipal)
        return RoomResponse.from(findRoom)
    }

    @Transactional
    fun createRoom(accommodationId: Long, request: CreateRoomRequest, userPrincipal: UserPrincipal): RoomResponse {
        val findAccommodation =
            accommodationRepository
                .findByIdOrNull(accommodationId) ?: throw ModelNotFoundException("Accommodation")
        checkBetweenOwnerAndLoginUser(findAccommodation.owner, userPrincipal)

        var uploadedImageStrings: MutableList<String>? = null
        if (!request.isPicsEmpty()) {
            uploadedImageStrings = s3Service.upload(request.roomPics, "room").toMutableList()
        }

        return roomRepository.save(request.to(findAccommodation, uploadedImageStrings)).let {
            RoomResponse.from(it)
        }

    }

    @Transactional
    fun updateRoom(roomId: Long, request: UpdateRoomRequest, userPrincipal: UserPrincipal): RoomResponse {
        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)
        checkBetweenOwnerAndLoginUser(findRoom.accommodation.owner, userPrincipal)

        var uploadedImageStrings: MutableList<String>? = null
        if (!request.isPicsEmpty()) {
            runBlocking {
                uploadedImageStrings = s3Service.upload(request.roomPics, "room").toMutableList()
            }
        }

        findRoom.update(request, uploadedImageStrings)

        return RoomResponse.from(findRoom)
    }

    @Transactional
    fun deleteRoom(roomId: Long, userPrincipal: UserPrincipal) {
        val findRoom = roomRepository.findByIdOrNull(roomId) ?: throw ModelNotFoundException("Room", roomId)
        checkBetweenOwnerAndLoginUser(findRoom.accommodation.owner, userPrincipal)
        roomRepository.delete(findRoom)
    }

    private fun checkBetweenOwnerAndLoginUser(owner: Owner, userPrincipal: UserPrincipal) {
        if (owner.id != userPrincipal.id) throw UnauthorizedException("이 숙박업소 객실에 대한 권한이 없습니다.")
    }
}
