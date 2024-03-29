package com.sparta.tobiro.domain.reservation.service

import com.sparta.tobiro.api.reservation.dto.request.CreateReservationRequest
import com.sparta.tobiro.api.reservation.dto.response.ReservationResponse
import com.sparta.tobiro.domain.accommodation.repository.RoomRepository
import com.sparta.tobiro.domain.member.repository.MemberRepository
import com.sparta.tobiro.domain.reservation.repository.ReservationRepository
import com.sparta.tobiro.global.exception.InvalidCredentialException
import com.sparta.tobiro.global.exception.ModelNotFoundException
import com.sparta.tobiro.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun createReservation(request: CreateReservationRequest): ReservationResponse {
        if (LocalDateTime.now()
                .isAfter(request.checkinDate.atTime(LocalTime.MAX))
        ) throw IllegalArgumentException("체크인 날짜가 현재 시각보다 과거일 수 없습니다.")
        else if (request.checkinDate.isAfter(request.checkoutDate)) throw IllegalArgumentException("체크인 날짜가 체크아웃 날짜보다 미래일 수 없습니다.")
        else if (request.checkinDate == request.checkoutDate) throw IllegalArgumentException("체크인 날짜와 체크아웃 날짜가 같을 수 없습니다.")

        val findRoom =
            roomRepository.findByIdOrNull(request.roomId) ?: throw ModelNotFoundException("Room", request.roomId)

        reservationRepository.existsByRoomAndCheckinDateBetween(
            findRoom,
            request.checkinDate.atTime(LocalTime.MIN),
            request.checkoutDate.atTime(LocalTime.NOON)
        ).let { isPossibleToBookAtCheckinDate ->
            if (isPossibleToBookAtCheckinDate) throw IllegalArgumentException("해당 체크인 날짜에 이미 예약이 존재합니다.")
        }
        reservationRepository.existsByRoomAndCheckoutDateBetween(
            findRoom,
            request.checkinDate.atTime(LocalTime.MAX),
            request.checkoutDate.atTime(LocalTime.NOON)
        ).let { isPossibleToBookAtCheckoutDate ->
            if (isPossibleToBookAtCheckoutDate) throw IllegalArgumentException("해당 체크아웃 날짜에 이미 예약이 존재합니다.")
        }

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        val findMember = memberRepository.findByIdOrNull(userPrincipal.id)
        findMember ?: throw ModelNotFoundException("Member", userPrincipal.id)

        if (findRoom.maxOccupancy < request.occupancy) throw IllegalArgumentException("수용인원 값은 방의 최대 수용인원 ${findRoom.maxOccupancy}보다 클 수 없습니다.")

        return ReservationResponse.from(reservationRepository.save(request.to(findMember, findRoom)))
    }

    fun getReservations(pageable: Pageable): Page<ReservationResponse> {
        return (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).let {
            val findMember = memberRepository.findByIdOrNull(it.id) ?: throw ModelNotFoundException("Model", it.id)
            if (findMember.id != it.id || it.authorities.first().authority != ("ROLE_MEMBER")) throw InvalidCredentialException()
            findMember
        }.let {
            reservationRepository.findAllByPageableAndMemberId(pageable, it.id!!)
        }.map { ReservationResponse.from(it) }
    }

    @Transactional
    fun deleteReservation(reservationId: Long) {
        val findReservation =
            reservationRepository.findByIdOrNull(reservationId) ?: throw ModelNotFoundException(
                "Reservation",
                reservationId
            )

        (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).let {
            if (findReservation.member.id != it.id) throw InvalidCredentialException()
        }

        reservationRepository.delete(findReservation)
    }
}