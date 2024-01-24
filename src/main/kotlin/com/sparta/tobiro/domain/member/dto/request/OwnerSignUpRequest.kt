package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.accommodation.model.Category
import com.sparta.tobiro.domain.member.validation.ValidName
import com.sparta.tobiro.domain.member.validation.ValidPassword

data class OwnerSignUpRequest(

    @field: ValidName
    var name: String,

    var email: String,

    @field: ValidPassword
    var password: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    var address: String,

    var businessNumber: String,

    var category : Category,

    var description:String,
    // var profilePic: MultiPartFile
)