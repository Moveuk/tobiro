package com.sparta.tobiro.domain.member.dto

data class UpdateMemberProfileRequest(

    var name: String,
    var email: String,
    var password: String,
    var introduction: String,
    var tlno: String,

)
