package com.sparta.tobiro.domain.accommodation.repository

import com.sparta.tobiro.domain.accommodation.model.RoomInfo
import org.springframework.data.jpa.repository.JpaRepository

interface RoomInfoRepository: JpaRepository<RoomInfo, Long> {

}