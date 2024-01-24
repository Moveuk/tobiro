package com.sparta.tobiro.global.exception

data class RoleNotFoundException(val role: String) : RuntimeException("알맞은 ${role}를 입력해주세요") {
}
