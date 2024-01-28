package com.sparta.tobiro.domain.member.dto.response

data class MemberResponse(

    var id: Long,

    var name: String,

    var email: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    var profilePicUrl: MutableList<String>,
)