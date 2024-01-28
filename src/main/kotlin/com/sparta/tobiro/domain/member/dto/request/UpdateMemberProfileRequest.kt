package com.sparta.tobiro.domain.member.dto.request

import com.sparta.tobiro.domain.member.validation.ValidTlno
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile

@Validated
data class UpdateMemberProfileRequest(
    var name: String,

    var introduction: String,

    @field: ValidTlno
    var tlno: String,

    var profilePic: MutableList<MultipartFile>
) {
    fun isPicsEmpty(): Boolean {
        return profilePic?.get(0)?.originalFilename == ""
    }
}
