package com.sparta.tobiro.domain.member.dto.request


data class LoginRequest(
    val email:String,
    val password: String,
    val role : String
)