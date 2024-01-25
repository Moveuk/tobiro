package com.sparta.tobiro.domain.accommodation.service

import com.sparta.tobiro.api.accommodation.dto.request.UpdateAccommodationRequest
import com.sparta.tobiro.api.accommodation.dto.response.AccommodationResponse
import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.repository.AccommodationRepository
import com.sparta.tobiro.domain.member.repository.OwnerRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
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
    private val ownerRepository: OwnerRepository
) {
    fun getMyAccommodation(principal: UserPrincipal): AccommodationResponse{
        val findAccommodation = ownerRepository.findByIdOrNull(principal.id).let {
            accommodationRepository.findByOwner(it!!) ?: throw ModelNotFoundException("Accommodation")
        }
        return AccommodationResponse.from(findAccommodation)
    }

    @Transactional
    fun updateMyAccommodation(principal: UserPrincipal, request: UpdateAccommodationRequest): AccommodationResponse{
        val findAccommodation = ownerRepository.findByIdOrNull(principal.id).let {
            accommodationRepository.findByOwner(it!!) ?: throw ModelNotFoundException("Accommodation")
        }
        findAccommodation.update(request)

        accommodationRepository.save(findAccommodation)

        return AccommodationResponse.from(findAccommodation)
    }

    fun searchAccommodations(query: String?, checkinDate: LocalDate, checkoutDate: LocalDate): Page<RoomResponse> {
        val pageRequest = PageRequest.of(0,5, Sort.Direction.DESC, "id")
        return accommodationRepository.searchAccommodations(pageRequest, query, checkinDate, checkoutDate)
            .map { RoomResponse.from(it) }
    }
}