package com.sparta.tobiro.global.exception

import com.sparta.tobiro.global.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class RoleExceptionHandler {
    @ExceptionHandler(RoleNotFoundException::class)
    fun handleRoleNotFoundException(e: RoleNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(e.message))
    }
}
