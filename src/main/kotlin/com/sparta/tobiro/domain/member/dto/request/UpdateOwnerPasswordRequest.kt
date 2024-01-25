package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.member.validation.ValidPassword

data class UpdateOwnerPasswordRequest(


    val ownerPassword:String,

    @field:ValidPassword
    val ownerNewPassword: String
)
