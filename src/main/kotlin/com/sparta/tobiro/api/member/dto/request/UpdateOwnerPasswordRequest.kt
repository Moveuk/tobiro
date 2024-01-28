package com.sparta.tobiro.api.member.dto.request

import com.sparta.tobiro.api.member.validation.ValidPassword

data class UpdateOwnerPasswordRequest(
    val ownerPassword:String,

    @field:ValidPassword
    val ownerNewPassword: String
)