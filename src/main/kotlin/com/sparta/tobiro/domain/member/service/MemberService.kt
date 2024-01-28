package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.api.member.dto.request.LoginRequest
import com.sparta.tobiro.api.member.dto.request.MemberSignUpRequest
import com.sparta.tobiro.api.member.dto.request.UpdateMemberPasswordRequest
import com.sparta.tobiro.api.member.dto.request.UpdateMemberProfileRequest
import com.sparta.tobiro.api.member.dto.response.LoginResponse
import com.sparta.tobiro.api.member.dto.response.MemberResponse


interface MemberService {
    fun signUp(request: MemberSignUpRequest): MemberResponse
    fun updateMemberProfile(memberId: Long, request: UpdateMemberProfileRequest): MemberResponse
    fun login(request: LoginRequest): LoginResponse
    fun updatePassword(memberId: Long, request: UpdateMemberPasswordRequest): String
}