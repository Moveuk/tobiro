package com.sparta.tobiro.domain.member.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table
class Owner(

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "profile_pic_url", nullable = false)
    var profilePicUrl: String = "https://imgur.com/S8jQ6wN",

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "tlno")
    var tlno: String,

    @Column(name = "adress")
    var adress: String,

    @Column(name = "created_Date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modified_Date")
    val modifiedDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.OWNER
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}