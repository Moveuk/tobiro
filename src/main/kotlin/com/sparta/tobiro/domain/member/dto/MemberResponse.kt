package com.sparta.tobiro.domain.member.dto

data class MemberResponse(

    var id: Long,

    var name: String,

    var email: String,

    var introduction: String,

    var tlno: String,

    var role: String,

    //var profilePicUrl: MultiPartFile ,

)


// MultiPartFile 클래스를 만들어서 사진 받은걸 URL 로 바꿔줘야한다