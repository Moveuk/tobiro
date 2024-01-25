package com.sparta.tobiro.api.accommodation.controller

import com.sparta.tobiro.api.accommodation.dto.response.RoomResponse
import com.sparta.tobiro.domain.accommodation.service.AccommodationService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    private val accommodationService: AccommodationService
) {

    @GetMapping("/accommodations")
    fun searchAccommodations(
        @RequestParam(name = "query") query: String?,
        @RequestParam(name = "checkin-date") checkinDate: LocalDate,
        @RequestParam(name = "checkout-date") checkoutDate: LocalDate,
    ): ResponseEntity<Page<RoomResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(accommodationService.searchAccommodations(query, checkinDate, checkoutDate))
    }
}