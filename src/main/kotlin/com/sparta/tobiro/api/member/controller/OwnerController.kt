package com.sparta.tobiro.api.member.controller


import com.sparta.tobiro.domain.member.dto.request.LoginRequest
import com.sparta.tobiro.domain.member.dto.request.OwnerSignUpRequest
import com.sparta.tobiro.domain.member.dto.request.UpdateOwnerProfileRequest
import com.sparta.tobiro.domain.member.dto.response.LoginResponse
import com.sparta.tobiro.domain.member.dto.response.OwnerResponse
import com.sparta.tobiro.domain.member.service.OwnerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RequestMapping("/api/v1/back-office/owners")
@RestController
class OwnerController(
    private val ownerService: OwnerService
) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest) : ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ownerService.login(loginRequest))
    }
    @PostMapping("/signup")
    fun signup(@RequestBody signUpRequest: OwnerSignUpRequest): ResponseEntity<OwnerResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ownerService.signUp(signUpRequest))
    }
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{ownerId}/profile")
    fun updateOwnerProfile(
        @PathVariable ownerId:Long,
        @RequestBody updateOwnerProfileRequest: UpdateOwnerProfileRequest
    ): ResponseEntity<OwnerResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ownerService.updateOwnerProfile(ownerId,updateOwnerProfileRequest))
    }
}

