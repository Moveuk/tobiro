package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.api.member.dto.request.LoginRequest
import com.sparta.tobiro.api.member.dto.request.OwnerSignUpRequest
import com.sparta.tobiro.api.member.dto.request.UpdateOwnerPasswordRequest
import com.sparta.tobiro.api.member.dto.request.UpdateOwnerProfileRequest
import com.sparta.tobiro.api.member.dto.response.LoginResponse
import com.sparta.tobiro.api.member.dto.response.OwnerResponse

interface OwnerService {
    fun signUp(request: OwnerSignUpRequest): OwnerResponse
    fun updateOwnerProfile(ownerId: Long, request: UpdateOwnerProfileRequest): OwnerResponse
    fun login(request: LoginRequest): LoginResponse
    fun updatePassword(ownerId: Long, request: UpdateOwnerPasswordRequest): String
}
