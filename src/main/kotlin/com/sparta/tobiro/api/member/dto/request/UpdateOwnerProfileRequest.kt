package com.sparta.tobiro.api.member.dto.request

import com.sparta.tobiro.api.member.validation.ValidBusinessNumber
import com.sparta.tobiro.api.member.validation.ValidTlno
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile

@Validated
data class UpdateOwnerProfileRequest(
    var name: String,

    var introduction: String,

    @field: ValidTlno
    var tlno: String,

    var address: String,

    @field : ValidBusinessNumber
    var businessNumber: String,

    var profilePic: MutableList<MultipartFile>
) {
    fun isPicsEmpty(): Boolean {
        return profilePic[0].originalFilename == ""
    }
}