package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.member.validation.ValidName
import com.sparta.tobiro.domain.member.validation.ValidPassword

data class MemberSignUpRequest(


    @field: ValidName
    var name: String,

    var email: String,

    @field: ValidPassword
    var password: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    //var profilePicUrl: MultiPartFile ,

)
