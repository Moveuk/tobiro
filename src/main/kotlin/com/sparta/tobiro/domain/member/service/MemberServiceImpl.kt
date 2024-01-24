package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.request.LoginRequest
import com.sparta.tobiro.domain.member.dto.request.MemberSignUpRequest
import com.sparta.tobiro.domain.member.dto.request.UpdateMemberProfileRequest
import com.sparta.tobiro.domain.member.dto.response.LoginResponse
import com.sparta.tobiro.domain.member.dto.response.MemberResponse
import com.sparta.tobiro.domain.member.model.Member
import com.sparta.tobiro.domain.member.model.Role
import com.sparta.tobiro.domain.member.model.toResponse
import com.sparta.tobiro.domain.member.repository.MemberRepository
import com.sparta.tobiro.global.exception.InvalidCredentialException
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.infra.security.MemberPrincipal
import com.sparta.tobiro.infra.security.jwt.JwtPlugin
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : MemberService {

    override fun login(request: LoginRequest): LoginResponse {
        val member = memberRepository.findByEmail(request.email) ?: throw ModelNotFoundException("Member")
        if (member.role.name != request.role) {
            throw InvalidCredentialException()
        }
        if (!passwordEncoder.matches(request.password, member.password)) {
            throw InvalidCredentialException()
        }
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
            throw IllegalStateException("이메일이 이미 사용중 입니다.")
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
                    else -> throw IllegalArgumentException("잘못된 role을 입력하셨습니다.")
                }
            )
        ).toResponse()
    }

    override fun updateMemberProfile(memberId: Long, request: UpdateMemberProfileRequest): MemberResponse {
        val loggerInMemberId: Long =(SecurityContextHolder.getContext().authentication.principal as MemberPrincipal).id
        if(memberId != loggerInMemberId) {
            throw IllegalArgumentException("프로필 수정 권한이 없습니다")
        }
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)
        member.name = request.name
        member.email = request.email
        member.introduction = request.introduction
        member.tlno = request.tlno
        return memberRepository.save(member).toResponse()
    }
}

//Password API 분리 하기

