package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.OwnerResponse
import com.sparta.tobiro.domain.member.dto.OwnerSignUpRequest
import com.sparta.tobiro.domain.member.dto.UpdateOwnerProfileRequest

interface OwnerService {
    fun signUp(request: OwnerSignUpRequest): OwnerResponse

    fun updateOwnerProfile(ownerId:Long,request: UpdateOwnerProfileRequest): OwnerResponse
}
