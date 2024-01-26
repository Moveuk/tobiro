package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.repository.AccommodationRepository
import com.sparta.tobiro.domain.member.dto.request.LoginRequest
import com.sparta.tobiro.domain.member.dto.request.OwnerSignUpRequest
import com.sparta.tobiro.domain.member.dto.request.UpdateOwnerPasswordRequest
import com.sparta.tobiro.domain.member.dto.request.UpdateOwnerProfileRequest
import com.sparta.tobiro.domain.member.dto.response.LoginResponse
import com.sparta.tobiro.domain.member.dto.response.OwnerResponse
import com.sparta.tobiro.domain.member.model.Owner
import com.sparta.tobiro.domain.member.model.OwnerRecentPasswords
import com.sparta.tobiro.domain.member.model.Role
import com.sparta.tobiro.domain.member.model.toResponse
import com.sparta.tobiro.domain.member.repository.OwnerRecentPasswordsRepository
import com.sparta.tobiro.domain.member.repository.OwnerRepository
import com.sparta.tobiro.global.exception.InvalidCredentialException
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.infra.security.UserPrincipal
import com.sparta.tobiro.infra.security.jwt.JwtPlugin
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class OwnerServiceImpl(
    private val ownerRepository: OwnerRepository,
    private val accommodationRepository: AccommodationRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val ownerRecentPasswordsRepository: OwnerRecentPasswordsRepository
) : OwnerService {

    @Transactional
    override fun updatePassword(ownerId: Long, request: UpdateOwnerPasswordRequest): String  {
        val owner = ownerRepository.findById(ownerId).orElseThrow { ModelNotFoundException("Owner", ownerId) }

        if (!passwordEncoder.matches(request.ownerPassword, owner.password)) {
            throw InvalidCredentialException("기존 비밀번호가 일치하지 않습니다.")
        }

        val newPassword = request.ownerNewPassword
        val newPasswordHash = passwordEncoder.encode(newPassword)

        val recentPasswords = ownerRecentPasswordsRepository.findTop3ByOwnerOrderByIdDesc(owner)
        if (recentPasswords.any { passwordEncoder.matches(newPassword, it.password) }) {
            throw IllegalArgumentException("최근 3번 사용한 비밀번호는 사용할 수 없습니다.")
        }
        val recentPassword = OwnerRecentPasswords(newPasswordHash, owner)
        ownerRecentPasswordsRepository.save(recentPassword)

        owner.password = newPasswordHash
        ownerRepository.save(owner)
        return "비밀번호 변경이 완료되었습니다"
    }
    override fun login(request: LoginRequest): LoginResponse {
        val owner = ownerRepository.findByEmail(request.email) ?: throw ModelNotFoundException("Owner")
        if (owner.role.name != request.role) {
            throw InvalidCredentialException()
        }
        if (!passwordEncoder.matches(request.password, owner.password)) {
            throw InvalidCredentialException()
        }
        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = owner.id.toString(),
                email = owner.email,
                role = owner.role.name
            ),
            name = owner.name,
            profilePicUrl = owner.profilePicUrl
        )
    }

    @Transactional
    override fun signUp(request: OwnerSignUpRequest): OwnerResponse {
        if (ownerRepository.existsByEmail(request.email)) {
            throw IllegalStateException("이메일이 이미 사용중 입니다.")
        }
        if (ownerRepository.existsByBusinessNumber(request.businessNumber)) {
            throw IllegalStateException("사업자번호가 이미 존재 합니다.")
        }
        val owner = Owner(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            introduction = request.introduction,
            tlno = request.tlno,
            address = request.address,
            businessNumber = request.businessNumber,
            role = when (request.role) {
                Role.OWNER.name -> Role.OWNER
                else -> throw IllegalArgumentException("잘못된 role을 입력하셨습니다.")
            }
        )
        val accommodation = accommodationRepository.save(
            Accommodation(
                owner = owner,
                category = request.category,
                accommodationPicUrls = "https://imgur.com/a/tBAKHUn",
                address = request.accommdationaddress,
                tlno = request.accommdationtlno,
                name = request.accommdationname,
                description = request.description
            )
        )
        return ownerRepository.save(owner).toResponse()
    }

    override fun updateOwnerProfile(ownerId: Long, request: UpdateOwnerProfileRequest): OwnerResponse {
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).id
        if (ownerId != authenticatedId) {
            throw IllegalArgumentException("프로필 수정 권한이 없습니다")
        }

        val owner = ownerRepository.findByIdOrNull(ownerId) ?: throw ModelNotFoundException("Owner", ownerId)
        owner.name = request.name
        owner.introduction = request.introduction
        owner.tlno = request.tlno
        owner.address = request.address
        owner.businessNumber = request.businessNumber
        return ownerRepository.save(owner).toResponse()
    }

}