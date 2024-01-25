package com.sparta.tobiro.domain.member.model

import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*


@Entity
@Table(name = "owner_recent_passwords")
class OwnerRecentPasswords (

    @Column(name = "owner_password", nullable = false)
    var password: String,

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    var owner: Owner,



    ) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}