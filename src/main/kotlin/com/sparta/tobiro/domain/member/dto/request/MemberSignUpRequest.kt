package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.member.validation.ValidPassword
import com.sparta.tobiro.domain.member.validation.ValidTlno
import jakarta.validation.constraints.Email
import org.springframework.validation.annotation.Validated

@Validated
data class MemberSignUpRequest(

    var name: String,

    @field:Email
    var email: String,

    @field: ValidPassword
    var password: String,

    var introduction: String,

    @field:ValidTlno
    var tlno: String,

    var role: String,
)
