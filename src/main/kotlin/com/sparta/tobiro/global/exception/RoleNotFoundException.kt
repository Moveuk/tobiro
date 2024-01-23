package com.sparta.tobiro.global.exception

data class RoleNotFoundException(val role: String) : RuntimeException("$role 를 입력해주세요") {
}
