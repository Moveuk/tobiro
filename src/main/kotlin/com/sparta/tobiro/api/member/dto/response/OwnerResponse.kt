package com.sparta.tobiro.api.member.dto.response

data class OwnerResponse(

    var id: Long,

    var name: String,

    var email: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    var address: String,

    var businessNumber: String,

    var profilePicUrl: MutableList<String>,
)