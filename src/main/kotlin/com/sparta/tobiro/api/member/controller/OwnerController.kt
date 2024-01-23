package com.sparta.tobiro.api.member.controller


import com.sparta.tobiro.domain.member.dto.OwnerResponse
import com.sparta.tobiro.domain.member.dto.OwnerSignUpRequest
import com.sparta.tobiro.domain.member.dto.UpdateOwnerProfileRequest
import com.sparta.tobiro.domain.member.service.OwnerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class OwnerController(
    private val ownerService: OwnerService
) {
    @PostMapping("/api/v1/back-office/owner/signup")
    fun signup(@RequestBody signUpRequest: OwnerSignUpRequest): ResponseEntity<OwnerResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ownerService.signUp(signUpRequest))
    }

    @PutMapping("/api/v1/back-office/{ownerId}/profile")
    fun updateOwnerProfile(
        @PathVariable ownerId:Long,
        @RequestBody updateOwnerProfileRequest: UpdateOwnerProfileRequest
    ): ResponseEntity<OwnerResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ownerService.updateOwnerProfile(ownerId,updateOwnerProfileRequest))
    }
}

