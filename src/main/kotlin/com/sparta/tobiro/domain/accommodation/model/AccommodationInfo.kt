package com.sparta.tobiro.domain.accommodation.model

import com.sparta.tobiro.domain.member.model.Owner
import com.sparta.tobiro.global.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "accommodation_info")
class AccommodationInfo(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var owner: Owner,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    var category: Category,

    @Column(name = "accommodation_pic_urls", nullable = false)
    var accommodationPicUrls: String = "https://imgur.com/a/tBAKHUn",

    @Column(name = "address", nullable = false)
    var address: String,

    @Column(name = "tlno", nullable = false)
    var tlno: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String
    ) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
