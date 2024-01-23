package com.sparta.tobiro.domain.member.dto

data class OwnerSignUpRequest(

    var name: String,

    var email: String,

    var password: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    var address: String,

    var businessNumber: String,

    // var profilePic: MultiPartFile
)