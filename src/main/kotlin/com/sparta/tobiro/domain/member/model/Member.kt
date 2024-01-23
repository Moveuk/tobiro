package com.sparta.tobiro.domain.member.model

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

    @Column(name = "profile_pic_url", nullable = false)
    var profilePicUrl: String = "https://imgur.com/S8jQ6wN",

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "tlno")
    var tlno: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.MEMBER

) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}

