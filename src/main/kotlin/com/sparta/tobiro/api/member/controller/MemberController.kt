package com.sparta.tobiro.api.member.controller

import com.sparta.tobiro.domain.member.dto.MemberResponse
import com.sparta.tobiro.domain.member.dto.MemberSignUpRequest
import com.sparta.tobiro.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/api/v1/member/signup")
    fun signUp(@RequestBody signUpRequest: MemberSignUpRequest): ResponseEntity<MemberResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.signUp(signUpRequest))
    }
}


/*
fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<MemberResponse> {
    val signupResponse = when (signUpRequest.role) {
        "MEMBER" -> memberService.signUp(signUpRequest)
        "OWNER" -> ownerService.signUp(signUpRequest)
        else -> throw IllegalArgumentException("정상적인 Role 아닙니다")
    }
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(signupResponse)
}
}
*/
