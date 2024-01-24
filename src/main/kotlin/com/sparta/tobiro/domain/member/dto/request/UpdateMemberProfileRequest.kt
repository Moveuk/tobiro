package com.sparta.tobiro.domain.member.dto.request

data class UpdateMemberProfileRequest(

    var name: String,
    var email: String,
    var introduction: String,
    var tlno: String,

)
