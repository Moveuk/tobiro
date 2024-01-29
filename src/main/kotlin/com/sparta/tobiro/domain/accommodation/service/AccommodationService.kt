package com.sparta.tobiro.domain.accommodation.service

import com.sparta.tobiro.api.accommodation.dto.request.UpdateAccommodationRequest
import com.sparta.tobiro.api.accommodation.dto.response.AccommodationDetailResponse
import com.sparta.tobiro.api.accommodation.dto.response.AccommodationResponse
import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.repository.AccommodationRepository
import com.sparta.tobiro.domain.accommodation.repository.RoomRepository
import com.sparta.tobiro.domain.member.repository.OwnerRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.infra.aws.S3Service
import com.sparta.tobiro.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class AccommodationService(
    private val accommodationRepository: AccommodationRepository,
    private val roomRepository: RoomRepository,
    private val ownerRepository: OwnerRepository,
    private val s3Service: S3Service
) {
    fun getMyAccommodation(principal: UserPrincipal): AccommodationDetailResponse{
        val findAccommodation = ownerRepository.findByIdOrNull(principal.id).let {
            accommodationRepository.findByOwner(it!!) ?: throw ModelNotFoundException("Accommodation")
        }
        return AccommodationDetailResponse.from(findAccommodation)
    }

    @Transactional
    fun updateMyAccommodation(principal: UserPrincipal, request: UpdateAccommodationRequest): AccommodationDetailResponse{
        val findAccommodation = ownerRepository.findByIdOrNull(principal.id).let {
            accommodationRepository.findByOwner(it!!) ?: throw ModelNotFoundException("Accommodation")
        }

        var uploadedImageStrings: MutableList<String>? = null
        if (!request.isPicsEmpty()) {
            uploadedImageStrings = s3Service.upload(request.accommodationPics, "accommodation").toMutableList()
        }

        findAccommodation.update(request, uploadedImageStrings)

        accommodationRepository.save(findAccommodation)

        return AccommodationDetailResponse.from(findAccommodation)
    }

    fun searchAccommodations(query: String?, checkinDate: LocalDate, checkoutDate: LocalDate): Page<RoomResponse> {
        val pageRequest = PageRequest.of(0, 5, Sort.Direction.DESC, "id")
        return accommodationRepository.searchAccommodations(pageRequest, query, checkinDate, checkoutDate)
            .map { RoomResponse.from(it) }
    }

    fun getAccommodation(accommodationId: Long): AccommodationResponse {
        val findAccommodation = accommodationRepository.findByIdOrNull(accommodationId)
        findAccommodation ?: throw ModelNotFoundException("Accommodation", accommodationId)

        val findRooms = roomRepository.findAllByAccommodation(findAccommodation)

        return AccommodationResponse.from(findAccommodation, findRooms)
    }
}