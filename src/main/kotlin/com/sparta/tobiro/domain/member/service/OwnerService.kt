package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.OwnerResponse
import com.sparta.tobiro.domain.member.dto.OwnerSignUpRequest

interface OwnerService {
    fun signUp(request: OwnerSignUpRequest): OwnerResponse
}
