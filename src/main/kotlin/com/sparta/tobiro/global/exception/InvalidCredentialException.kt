package com.sparta.tobiro.global.exception


data class InvalidCredentialException(
    override val message: String? = "자격이 유효하지 않습니다"
) : RuntimeException()