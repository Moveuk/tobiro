package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.request.LoginRequest
import com.sparta.tobiro.domain.member.dto.request.OwnerSignUpRequest
import com.sparta.tobiro.domain.member.dto.request.UpdateOwnerProfileRequest
import com.sparta.tobiro.domain.member.dto.response.LoginResponse
import com.sparta.tobiro.domain.member.dto.response.OwnerResponse
import com.sparta.tobiro.domain.member.model.Owner
import com.sparta.tobiro.domain.member.model.Role
import com.sparta.tobiro.domain.member.model.toResponse
import com.sparta.tobiro.domain.member.repository.OwnerRepository
import com.sparta.tobiro.global.exception.InvalidCredentialException
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.infra.security.jwt.JwtPlugin
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class OwnerServiceImpl(
    private val ownerRepository: OwnerRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
) : OwnerService {
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
            )
        )
    }
    @Transactional
    override fun signUp(request: OwnerSignUpRequest): OwnerResponse {
        if (ownerRepository.existsByEmail(request.email)) {
            throw IllegalStateException("이메일이 이미 사용중 입니다.")
        }
        return ownerRepository.save(
            Owner(
                //패스워드 암호화하기
                password = passwordEncoder.encode(request.password),
                email = request.email,
                introduction = request.introduction,
                tlno = request.tlno,
                name = request.name,
                address = request.address,
                businessNumber = request.businessNumber,
                role = when (request.role) {
                    Role.OWNER.name -> Role.OWNER
                    else -> throw IllegalArgumentException("잘못된 role을 입력하셨습니다.")
                }
            )
        ).toResponse()
    }

    override fun updateOwnerProfile(ownerId: Long, request: UpdateOwnerProfileRequest): OwnerResponse {
        val owner = ownerRepository.findByIdOrNull(ownerId) ?: throw ModelNotFoundException("Owner", ownerId)
        owner.name = request.name
        owner.email = request.email
        owner.introduction = request.introduction
        owner.tlno = request.tlno
        owner.address = request.address
        owner.businessNumber = request.businessNumber
        return ownerRepository.save(owner).toResponse()
    }

}