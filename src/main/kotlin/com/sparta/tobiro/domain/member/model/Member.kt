package com.sparta.tobiro.domain.member.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "Member")
class Member(

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "profile_Pic_url", nullable = false)
    var profile_Pic_Url: String = "https://imgur.com/S8jQ6wN",

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "tlno")
    var tlno: String,

    @Column(name = "created_date")
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modified_date")
    var modifiedDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.MEMBER

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}

