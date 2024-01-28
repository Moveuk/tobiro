package com.sparta.tobiro.api.member.dto.request

import com.sparta.tobiro.api.member.validation.ValidPassword


data class UpdateMemberPasswordRequest(
    @field: ValidPassword
    val memberPassword:String,

    @field: ValidPassword
    val memberNewPassword: String
)
