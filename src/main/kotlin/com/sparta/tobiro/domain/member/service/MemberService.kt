package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.MemberResponse
import com.sparta.tobiro.domain.member.dto.MemberSignUpRequest


interface MemberService {
    fun signUp(request: MemberSignUpRequest): MemberResponse
}