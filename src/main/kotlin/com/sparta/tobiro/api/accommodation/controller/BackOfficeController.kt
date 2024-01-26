package com.sparta.tobiro.api.accommodation.controller

import com.sparta.tobiro.api.accommodation.dto.request.CreateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.request.UpdateAccommodationRequest
import com.sparta.tobiro.api.accommodation.dto.request.UpdateRoomRequest
import com.sparta.tobiro.api.accommodation.dto.response.AccommodationDetailResponse
import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.service.AccommodationService
import com.sparta.tobiro.domain.accommodation.service.RoomService
import com.sparta.tobiro.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/back-office")
class BackOfficeController(
    private val accommodationService: AccommodationService,
    private val roomService: RoomService,
) {
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/my-accommodation")
    fun getMyAccommodation(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<AccommodationDetailResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accommodationService.getMyAccommodation(userPrincipal))
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/my-accommodation")
    fun updateMyAccommodation(
        @RequestBody request: UpdateAccommodationRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<AccommodationDetailResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accommodationService.updateMyAccommodation(userPrincipal, request))
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/accommodations/{accommodationId}/rooms")
    fun getRooms(
        @PathVariable accommodationId: Long,
        @PageableDefault(size = 10, sort = ["id"]) pageable: Pageable,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Page<RoomResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(roomService.getRooms(accommodationId, pageable, userPrincipal))
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/accommodations/{accommodationId}/rooms/{roomId}")
    fun getRoom(
        @PathVariable roomId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<RoomResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(roomService.getRoom(roomId, userPrincipal))
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/accommodations/{accommodationId}/rooms")
    fun createRoom(
        @PathVariable accommodationId: Long,
        @RequestBody request: CreateRoomRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<RoomResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(roomService.createRoom(accommodationId, request, userPrincipal))
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/accommodations/{accommodationId}/rooms/{roomId}")
    fun updateRoom(
        @PathVariable roomId: Long,
        @RequestBody request: UpdateRoomRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<RoomResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(roomService.updateRoom(roomId, request, userPrincipal))
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/accommodations/{accommodationId}/rooms/{roomId}")
    fun deleteRoom(
        @PathVariable roomId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        return roomService.deleteRoom(roomId, userPrincipal).let {
            ResponseEntity.noContent().build()
        }
    }
}