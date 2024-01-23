package com.sparta.tobiro.api.accommodation.controller

import com.sparta.tobiro.api.accommodation.dto.request.UpdateAccommodationRequest
import com.sparta.tobiro.api.accommodation.dto.response.AccommodationResponse
import com.sparta.tobiro.domain.accommodation.service.AccommodationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/back-office")
class BackOfficeController(
    private val accommodationService: AccommodationService
) {
    @GetMapping("/my-accommodation")
    fun getMyAccommodation(
        authentication: Authentication?
    ): ResponseEntity<AccommodationResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accommodationService.getMyAccommodation(authentication))
    }

    @PostMapping("/my-accommodation")
    fun updateMyAccommodation(
        @RequestBody request: UpdateAccommodationRequest,
        authentication: Authentication?
    ): ResponseEntity<AccommodationResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accommodationService.updateMyAccommodation(authentication, request))
    }
}