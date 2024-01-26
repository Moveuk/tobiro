package com.sparta.tobiro.domain.member.model

import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*


@Entity
@Table(name = "member_recent_passwords")
class MemberRecentPasswords (

    @Column(name = "member_password", nullable = false)
    var password: String,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    ) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}