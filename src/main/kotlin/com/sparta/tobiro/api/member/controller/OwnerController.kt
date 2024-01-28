package com.sparta.tobiro.api.member.controller


import com.sparta.tobiro.api.member.dto.request.LoginRequest
import com.sparta.tobiro.api.member.dto.request.OwnerSignUpRequest
import com.sparta.tobiro.api.member.dto.request.UpdateOwnerPasswordRequest
import com.sparta.tobiro.api.member.dto.request.UpdateOwnerProfileRequest
import com.sparta.tobiro.api.member.dto.response.LoginResponse
import com.sparta.tobiro.api.member.dto.response.OwnerResponse
import com.sparta.tobiro.domain.member.service.OwnerService
import com.sparta.tobiro.infra.security.UserPrincipal
import jakarta.validation.Valid
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RequestMapping("/api/v1/back-office/owners")
@RestController
class OwnerController(
    private val ownerService: OwnerService
) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ownerService.login(loginRequest))
    }

    @PostMapping(
        "/signup",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun signup(
        @Valid @ModelAttribute signUpRequest: OwnerSignUpRequest
    ): ResponseEntity<OwnerResponse> {
        return runBlocking {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.signUp(signUpRequest))
        }
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{ownerId}/profile",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateOwnerProfile(
        @PathVariable ownerId: Long,
        @Valid @ModelAttribute updateOwnerProfileRequest: UpdateOwnerProfileRequest
    ): ResponseEntity<OwnerResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ownerService.updateOwnerProfile(ownerId, updateOwnerProfileRequest))
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/my-password")
    fun updatePassword(@Valid @RequestBody request: UpdateOwnerPasswordRequest): ResponseEntity<String> {
        val authenticatedId = (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).id
        val message = ownerService.updatePassword(authenticatedId, request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(message)
    }
}