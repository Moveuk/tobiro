package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.member.validation.ValidPassword
import jakarta.validation.constraints.Email
import org.springframework.validation.annotation.Validated

@Validated
data class LoginRequest(
    @field: Email
    val email:String,
    @field: ValidPassword
    val password: String,
    val role : String
)