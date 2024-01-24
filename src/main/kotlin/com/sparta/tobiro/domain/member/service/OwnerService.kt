package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.*

interface OwnerService {
    fun signUp(request: OwnerSignUpRequest): OwnerResponse
    fun updateOwnerProfile(ownerId:Long,request: UpdateOwnerProfileRequest): OwnerResponse

    fun login(request: LoginRequest): LoginResponse
}
