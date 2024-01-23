package com.sparta.tobiro.domain.member.dto

data class MemberSignUpRequest(

    var name: String,

    var email: String,

    var password: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    //var profilePicUrl: MultiPartFile ,

)
