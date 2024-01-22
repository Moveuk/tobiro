package com.sparta.tobiro.domain.accommodation.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="accommodation_info")
class AccommodationInfo(

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    var parent: Owner?,

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
    var description: String,

    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modified_date", nullable = false)
    var modifiedDate: LocalDateTime = LocalDateTime.now(),

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
