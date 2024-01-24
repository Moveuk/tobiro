package com.sparta.tobiro.domain.member.dto.request

data class UpdateOwnerProfileRequest(
    var name: String,
    var email: String,
    var password: String,
    var introduction: String,
    var tlno: String,
    var address: String,
    var businessNumber: String,
)