package com.sparta.tobiro.api.member.controller

import com.sparta.tobiro.api.member.dto.request.LoginRequest
import com.sparta.tobiro.api.member.dto.request.MemberSignUpRequest
import com.sparta.tobiro.api.member.dto.request.UpdateMemberPasswordRequest
import com.sparta.tobiro.api.member.dto.request.UpdateMemberProfileRequest
import com.sparta.tobiro.api.member.dto.response.LoginResponse
import com.sparta.tobiro.api.member.dto.response.MemberResponse
import com.sparta.tobiro.domain.member.service.MemberService
import com.sparta.tobiro.infra.security.UserPrincipal
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService,
) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.login(loginRequest))
    }
    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody signUpRequest: MemberSignUpRequest): ResponseEntity<MemberResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.signUp(signUpRequest))
    }
    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping("/{memberId}/profile")
    fun updateMemberProfile(
        @PathVariable memberId:Long,
        @Valid @ModelAttribute updateMemberProfileRequest: UpdateMemberProfileRequest
    ): ResponseEntity<MemberResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.updateMemberProfile(memberId,updateMemberProfileRequest))
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping("/my-password")
    fun updatePassword(@Valid @RequestBody request: UpdateMemberPasswordRequest):ResponseEntity<String>{
        val authenticatedId = (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).id
        val message = memberService.updatePassword(authenticatedId, request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(message)
    }
}
