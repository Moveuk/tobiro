package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.*


interface MemberService {
    fun signUp(request: MemberSignUpRequest): MemberResponse
    fun updateMemberProfile(memberId:Long,request:UpdateMemberProfileRequest):MemberResponse

    fun login(request: LoginRequest): LoginResponse
}