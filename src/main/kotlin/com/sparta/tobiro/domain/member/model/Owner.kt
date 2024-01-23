package com.sparta.tobiro.domain.member.model

import com.sparta.tobiro.domain.member.dto.OwnerResponse
import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "owner")
class Owner(

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "tlno")
    var tlno: String,

    @Column(name = "address")
    var address: String,

    @Column(name = "businessNumber")
    var businessNumber: String,

    //   @Column(name = "profile_pic_url", nullable = false)
    //   var profilePicUrl: String = "https://imgur.com/S8jQ6wN",

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.OWNER

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun Owner.toResponse(): OwnerResponse {
    return OwnerResponse(
        id = id!!,
        name = name,
        email = email,
        role = role.name,
        introduction = introduction,
        tlno = tlno,
        address = address,
        businessNumber = businessNumber
    )
}
