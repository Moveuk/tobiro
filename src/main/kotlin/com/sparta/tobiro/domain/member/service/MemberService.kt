package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.MemberResponse
import com.sparta.tobiro.domain.member.dto.MemberSignUpRequest
import com.sparta.tobiro.domain.member.dto.UpdateMemberProfileRequest


interface MemberService {
    fun signUp(request: MemberSignUpRequest): MemberResponse

    fun updateMemberProfile(memberId:Long,request:UpdateMemberProfileRequest):MemberResponse
}