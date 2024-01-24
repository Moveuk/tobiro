package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.accommodation.model.Category

data class OwnerSignUpRequest(

    var name: String,

    var email: String,

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