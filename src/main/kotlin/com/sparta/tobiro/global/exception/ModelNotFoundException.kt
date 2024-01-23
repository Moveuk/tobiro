package com.sparta.tobiro.global.exception

data class ModelNotFoundException(val model: String, val id: Long?) : RuntimeException("$model $id 가 존재하지 않습니다.") {
}