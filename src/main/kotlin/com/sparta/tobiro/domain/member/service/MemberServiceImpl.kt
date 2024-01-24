package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.*
import com.sparta.tobiro.domain.member.model.Member
import com.sparta.tobiro.domain.member.model.Role
import com.sparta.tobiro.domain.member.model.toResponse
import com.sparta.tobiro.domain.member.repository.MemberRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.global.exception.RoleNotFoundException
import com.sparta.tobiro.infra.security.jwt.JwtPlugin
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : MemberService {

    override fun login(request: LoginRequest): LoginResponse {
        val member = memberRepository.findByEmail(request.email) ?: throw RoleNotFoundException("Email")
        if(member.role.name != request.role ){ throw RoleNotFoundException("MEMBER")}
        if (!passwordEncoder.matches(request.password,member.password)){ throw RoleNotFoundException("password")}

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = member.id.toString(),
                email = member.email,
                role = member.role.name
            )
        )
    }

    @Transactional
    override fun signUp(request: MemberSignUpRequest): MemberResponse {
        if (memberRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email is already in use")
        }
        return memberRepository.save(
            Member(
                //패스워드 암호화하기
                password = passwordEncoder.encode(request.password),
                email = request.email,
                introduction = request.introduction,
                tlno = request.tlno,
                name = request.name,
                role = when (request.role) {
                    Role.MEMBER.name -> Role.MEMBER
                    else -> throw RoleNotFoundException("MEMBER")
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

