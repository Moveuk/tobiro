package com.sparta.tobiro.domain.member.model

import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "admin")
class Admin(

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.ADMIN,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}