package com.sparta.tobiro.domain.member.dto

data class OwnerResponse(

    var id: Long,

    var name: String,

    var email: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    var address: String,

    var businessNumber: String,

    //var profilePicUrl: String = "https://imgur.com/S8jQ6wN",


)