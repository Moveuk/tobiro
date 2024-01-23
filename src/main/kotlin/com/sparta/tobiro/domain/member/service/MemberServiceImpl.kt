package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.MemberResponse
import com.sparta.tobiro.domain.member.dto.MemberSignUpRequest
import com.sparta.tobiro.domain.member.dto.UpdateMemberProfileRequest
import com.sparta.tobiro.domain.member.model.Member
import com.sparta.tobiro.domain.member.model.Role
import com.sparta.tobiro.domain.member.model.toResponse
import com.sparta.tobiro.domain.member.repository.MemberRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
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

    override fun updateMemberProfile(memberId: Long, request: UpdateMemberProfileRequest): MemberResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)
        member.name = request.name
        member.email = request.email
        member.introduction = request.introduction
        member.tlno = request.tlno
        return memberRepository.save(member).toResponse()
    }
}

//Password API 분리 하기

