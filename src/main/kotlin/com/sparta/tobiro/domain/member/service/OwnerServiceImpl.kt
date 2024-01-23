package com.sparta.tobiro.domain.member.service

import com.sparta.tobiro.domain.member.dto.OwnerResponse
import com.sparta.tobiro.domain.member.dto.OwnerSignUpRequest
import com.sparta.tobiro.domain.member.dto.UpdateOwnerProfileRequest
import com.sparta.tobiro.domain.member.model.Owner
import com.sparta.tobiro.domain.member.model.Role
import com.sparta.tobiro.domain.member.model.toResponse
import com.sparta.tobiro.domain.member.repository.OwnerRepository
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.global.exception.RoleNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OwnerServiceImpl(
    private val ownerRepository: OwnerRepository
) : OwnerService {

    @Transactional
    override fun signUp(request: OwnerSignUpRequest): OwnerResponse {
        if (ownerRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email is already in use")
        }
        return ownerRepository.save(
            Owner(
                //패스워드 암호화하기
                password = request.password,
                email = request.email,
                introduction = request.introduction,
                tlno = request.tlno,
                name = request.name,
                address = request.address,
                businessNumber = request.businessNumber,
                role = when (request.role) {
                    Role.OWNER.name -> Role.OWNER
                    else -> throw RoleNotFoundException("OWNER")
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