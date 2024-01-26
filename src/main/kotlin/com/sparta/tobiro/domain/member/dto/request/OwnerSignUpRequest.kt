package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.accommodation.model.Category
import com.sparta.tobiro.domain.member.validation.ValidBusinessNumber
import com.sparta.tobiro.domain.member.validation.ValidName
import com.sparta.tobiro.domain.member.validation.ValidPassword
import com.sparta.tobiro.domain.member.validation.ValidTlno
import jakarta.validation.constraints.Email
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile

@Validated
data class OwnerSignUpRequest(
    @field: ValidName
    var name: String,

    @field: Email
    var email: String,

    @field: ValidPassword
    var password: String,

    var introduction: String,

    @field:ValidTlno
    var tlno: String,

    var role: String,

    var address: String,

    @field:ValidBusinessNumber
    var businessNumber: String,

    var category: Category,

    var description: String,

    var accommodationPic: MultipartFile?
)