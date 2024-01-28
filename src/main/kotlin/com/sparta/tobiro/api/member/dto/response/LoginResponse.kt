package com.sparta.tobiro.api.member.dto.response

data class LoginResponse(
    val accessToken: String,
    val name: String,
    val profilePicUrl: MutableList<String>
)