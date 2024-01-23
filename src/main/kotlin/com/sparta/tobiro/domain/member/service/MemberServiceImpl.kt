package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.MemberResponse
import com.sparta.tobiro.domain.member.dto.MemberSignUpRequest
import com.sparta.tobiro.domain.member.model.Member
import com.sparta.tobiro.domain.member.model.Role
import com.sparta.tobiro.domain.member.model.toResponse
import com.sparta.tobiro.domain.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
) : MemberService {

    @Transactional
    override fun signUp(request: MemberSignUpRequest): MemberResponse {
        if (memberRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email is already in use")
        }
        return memberRepository.save(
            Member(
                //패스워드 암호화하기
                password = request.password,
                email = request.email,
                introduction = request.introduction,
                tlno = request.tlno,
                name = request.name,
                role = when (request.role) {
                    Role.MEMBER.name -> Role.MEMBER
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }
}

