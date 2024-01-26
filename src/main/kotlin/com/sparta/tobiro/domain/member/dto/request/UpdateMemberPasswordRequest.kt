package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.member.validation.ValidPassword


data class UpdateMemberPasswordRequest(


    val memberPassword:String,

    @field: ValidPassword
    val memberNewPassword: String
)
