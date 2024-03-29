package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.api.member.dto.request.LoginRequest
import com.sparta.tobiro.api.member.dto.request.MemberSignUpRequest
import com.sparta.tobiro.api.member.dto.request.UpdateMemberPasswordRequest
import com.sparta.tobiro.api.member.dto.request.UpdateMemberProfileRequest
import com.sparta.tobiro.api.member.dto.response.LoginResponse
import com.sparta.tobiro.api.member.dto.response.MemberResponse
import com.sparta.tobiro.domain.member.model.Member
import com.sparta.tobiro.domain.member.model.MemberRecentPasswords
import com.sparta.tobiro.domain.member.model.Role
import com.sparta.tobiro.domain.member.model.toResponse
import com.sparta.tobiro.domain.member.repository.MemberRecentPasswordsRepository
import com.sparta.tobiro.domain.member.repository.MemberRepository
import com.sparta.tobiro.global.exception.InvalidCredentialException
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.infra.aws.S3Service
import com.sparta.tobiro.infra.security.UserPrincipal
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
    private val memberRecentPasswordsRepository: MemberRecentPasswordsRepository,
    private val jwtPlugin: JwtPlugin,
    private val s3Service: S3Service
) : MemberService {

    @Transactional
    override fun updatePassword(memberId: Long, request: UpdateMemberPasswordRequest): String {

        val member = memberRepository.findById(memberId).orElseThrow { ModelNotFoundException("Member", memberId) }
        if (!passwordEncoder.matches(request.memberPassword, member.password)) {
            throw InvalidCredentialException("기존 비밀번호가 일치하지 않습니다.")
        }

        val newPassword = request.memberNewPassword
        val newPasswordHash = passwordEncoder.encode(newPassword)

        val recentPasswords = memberRecentPasswordsRepository.findTop3ByMemberOrderByIdDesc(member)
        if (recentPasswords.any { passwordEncoder.matches(newPassword, it.password) }) {
            throw IllegalArgumentException("최근 3번 사용한 비밀번호는 사용할 수 없습니다.")
        }

        val recentPassword = MemberRecentPasswords(newPasswordHash, member)
        memberRecentPasswordsRepository.save(recentPassword)

        member.password = newPasswordHash
        memberRepository.save(member)
        return "비밀번호 변경이 완료되었습니다"
    }

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
            ),
            name = member.name,
            profilePicUrl = member.profilePicUrl
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
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).id
        if (memberId != authenticatedId) {
            throw IllegalArgumentException("프로필 수정 권한이 없습니다")
        }
        var uploadedImageStrings: MutableList<String>? = null
        if (!request.isPicsEmpty()) {
            uploadedImageStrings = s3Service.upload(request.profilePic, "member").toMutableList()
        }
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)
        member.name = request.name
        member.introduction = request.introduction
        member.tlno = request.tlno
        if (uploadedImageStrings != null) {
            member.profilePicUrl = uploadedImageStrings
        }
        return memberRepository.save(member).toResponse()
    }
}