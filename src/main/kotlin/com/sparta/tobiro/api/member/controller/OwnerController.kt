package com.sparta.tobiro.api.member.controller


import com.sparta.tobiro.domain.member.dto.*
import com.sparta.tobiro.domain.member.service.OwnerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class OwnerController(
    private val ownerService: OwnerService
) {
    @PostMapping("/api/v1/owner/login")
    fun login(@RequestBody loginRequest: LoginRequest) : ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ownerService.login(loginRequest))
    }
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

