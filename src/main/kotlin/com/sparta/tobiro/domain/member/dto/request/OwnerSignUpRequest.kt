package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.accommodation.model.Accommodation
import com.sparta.tobiro.domain.accommodation.model.Category
import com.sparta.tobiro.domain.member.model.Owner
import com.sparta.tobiro.domain.member.validation.ValidBusinessNumber
import com.sparta.tobiro.domain.member.validation.ValidPassword
import com.sparta.tobiro.domain.member.validation.ValidTlno
import jakarta.validation.constraints.Email
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile

@Validated
data class OwnerSignUpRequest(

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

    var accommodationName: String,

    var accommodationAddress: String,

    var accommodationTlno: String,

    var category: Category,

    var description: String,

    var accommodationPics: MutableList<MultipartFile>
) {
    fun isPicsEmpty(): Boolean {
        return accommodationPics?.get(0)?.originalFilename == ""
    }

    fun toEntity(owner: Owner, request: OwnerSignUpRequest, uploadedImageStrings: MutableList<String>?): Accommodation {
        return Accommodation(
            owner = owner,
            category = request.category,
            accommodationPicUrls = uploadedImageStrings ?: mutableListOf("https://imgur.com/a/tBAKHUn"),
            address = request.accommodationAddress,
            tlno = request.accommodationTlno,
            name = request.accommodationName,
            description = request.description
        )
    }
}