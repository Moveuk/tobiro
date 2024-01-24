package com.sparta.tobiro.domain.member.model

import com.sparta.tobiro.domain.member.dto.response.MemberResponse
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

    //  @Column(name = "profile_pic_url", nullable = false)
    // var profilePicUrl: MultiPartFile ,


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.MEMBER

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
    )
}
