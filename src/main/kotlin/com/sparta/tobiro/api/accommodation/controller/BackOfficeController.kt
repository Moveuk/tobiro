package com.sparta.tobiro.api.accommodation.controller

import com.sparta.tobiro.api.accommodation.dto.request.CreateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.request.UpdateAccommodationRequest
import com.sparta.tobiro.api.accommodation.dto.response.AccommodationResponse
import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.service.AccommodationService
import com.sparta.tobiro.domain.accommodation.service.RoomService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/back-office")
class BackOfficeController(
    private val accommodationService: AccommodationService,
    private val roomService: RoomService,
) {
    @GetMapping("/my-accommodation")
    fun getMyAccommodation(
        authentication: Authentication?
    ): ResponseEntity<AccommodationResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accommodationService.getMyAccommodation(authentication))
    }

    @PutMapping("/my-accommodation")
    fun updateMyAccommodation(
        @RequestBody request: UpdateAccommodationRequest,
        authentication: Authentication?
    ): ResponseEntity<AccommodationResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accommodationService.updateMyAccommodation(authentication, request))
    }

    @GetMapping("/accommodations/{accommodationId}/rooms")
    fun getRooms(
        @PathVariable accommodationId: Long,
        @PageableDefault(size = 10, sort = ["id"]) pageable: Pageable,
        authentication: Authentication?,
    ): ResponseEntity<Page<RoomResponse>> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(roomService.getRooms(accommodationId, pageable, authentication))
    }

    @PostMapping("/accommodations/{accommodationId}/rooms")
    fun createRoom(
        @PathVariable accommodationId: Long,
        @RequestBody request: CreateRoomRequest,
        authentication: Authentication?,
    ): ResponseEntity<RoomResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(roomService.createRoom(accommodationId, request))
    }
}