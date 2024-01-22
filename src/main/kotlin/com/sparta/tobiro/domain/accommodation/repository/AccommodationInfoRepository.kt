package com.sparta.tobiro.domain.accommodation.repository

import com.sparta.tobiro.domain.accommodation.model.AccommodationInfo
import org.springframework.data.jpa.repository.JpaRepository

interface AccommodationInfoRepository: JpaRepository<AccommodationInfo, Long> {

}