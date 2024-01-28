package com.sparta.tobiro.global.exception

data class UnauthorizedException(override val message: String) : RuntimeException(message) {

}
