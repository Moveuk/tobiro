package com.sparta.tobiro.domain.member.model

import com.sparta.tobiro.api.member.dto.response.MemberResponse
import com.sparta.tobiro.global.StringMutableListConverter
import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*


@Entity
@Table(name = "member")
class Member(

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "tlno")
    var tlno: String,

    @Column(name = "profile_pic_url", nullable = false)
    @Convert(converter = StringMutableListConverter::class)
    var profilePicUrl: MutableList<String> = mutableListOf("https://imgur.com/S8jQ6wN"),

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.MEMBER,

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var memberRecentPasswords: MutableList<MemberRecentPasswords> = mutableListOf()

) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}

fun Member.toResponse(): MemberResponse {
    return MemberResponse(
        id = id!!,
        name = name,
        email = email,
        role = role.name,
        introduction = introduction,
        tlno = tlno,
        profilePicUrl = profilePicUrl
    )
}
