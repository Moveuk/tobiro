package com.sparta.tobiro.domain.member.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
data class Profile(

    @Column(name = "name", nullable = false)
    var name: String,
)


