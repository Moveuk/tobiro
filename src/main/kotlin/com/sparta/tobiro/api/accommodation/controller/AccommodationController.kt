package com.sparta.tobiro.api.accommodation.controller

import com.sparta.tobiro.api.accommodation.dto.response.AccommodationResponse
import com.sparta.tobiro.domain.accommodation.service.AccommodationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accommodations")
class AccommodationController(
    private val accommodationService: AccommodationService
) {
    @GetMapping("/{accommodationId}")
    fun getAccommodation(
        @PathVariable accommodationId: Long
    ):ResponseEntity<AccommodationResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(accommodationService.getAccommodation(accommodationId))
    }
}