package com.sparta.tobiro.domain.member.dto.response

data class LoginResponse(
    val accessToken: String,
    val name: String,
    val profilePicUrl: String
)