package com.sparta.tobiro.domain.reservation.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.PathBuilder
import com.sparta.tobiro.domain.accommodation.model.QAccommodation
import com.sparta.tobiro.domain.accommodation.model.QRoom
import com.sparta.tobiro.domain.member.model.QMember
import com.sparta.tobiro.domain.reservation.model.QReservation
import com.sparta.tobiro.domain.reservation.model.Reservation
import com.sparta.tobiro.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class CustomReservationRepositoryImpl : CustomReservationRepository, QueryDslSupport() {
    private val reservation = QReservation.reservation
    private val accommodation = QAccommodation.accommodation
    private val room = QRoom.room
    private val member = QMember.member

    override fun findAllByPageableAndMemberId(
        pageable: Pageable,
        memberId: Long
    ): Page<Reservation> {
        val whereClause = BooleanBuilder()

        whereClause.and(reservation.member.id.eq(memberId))

        val contents: MutableList<Reservation>
        val totalCount:
                Long
        try {
            contents = queryFactory
                .selectFrom(reservation)
                .where(whereClause)
                .leftJoin(reservation.room, room)
                .fetchJoin()
                .leftJoin(reservation.member, member)
                .fetchJoin()
                .offset(pageable.offset)
                .orderBy(*getOrderSpecifier(pageable, reservation))
                .fetch()

            totalCount = queryFactory
                .select(reservation.count())
                .from(reservation)
                .where(whereClause)
                .fetchOne() ?: 0L
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(
                "잘못된 sort 인자 ${
                    pageable.sort.toList().map { it.property }.joinToString(",")
                }를 넣으셨습니다."
            )
        } catch (e: Exception) {
            throw IllegalArgumentException("exception")
        }

        return PageImpl(contents, pageable, totalCount)
    }

    private fun getOrderSpecifier(pageable: Pageable, path: EntityPath<*>): Array<OrderSpecifier<*>> {
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map { order ->
            OrderSpecifier(
                if (order.isAscending) Order.ASC else Order.DESC,
                pathBuilder.get(order.property) as Expression<Comparable<*>>
            )
        }.toTypedArray()
    }
}