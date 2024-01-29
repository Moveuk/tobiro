package com.sparta.tobiro.domain.accommodation.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.JPAExpressions
import com.sparta.tobiro.domain.accommodation.model.QAccommodation
import com.sparta.tobiro.domain.accommodation.model.QRoom
import com.sparta.tobiro.domain.accommodation.model.Room
import com.sparta.tobiro.domain.reservation.model.QReservation
import com.sparta.tobiro.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime

@Repository
class CustomRoomRepositoryImpl : CustomRoomRepository, QueryDslSupport() {
    private val room = QRoom.room
    private val accommodation = QAccommodation.accommodation
    private val reservation = QReservation.reservation

    override fun findAllByPageableAndAccommodationId(
        pageable: Pageable,
        accommodationId: Long
    ): Page<Room> {
        val whereClause = BooleanBuilder()

        whereClause.and(room.accommodation.id.eq(accommodationId))

        val contents: MutableList<Room>
        val totalCount:
                Long
        try {
            contents = queryFactory
                .selectFrom(room)
                .where(whereClause)
                .leftJoin(room.accommodation, accommodation)
                .fetchJoin()
                .offset(pageable.offset)
                .orderBy(*getOrderSpecifier(pageable, room))
                .fetch()

            totalCount = queryFactory
                .select(room.count())
                .from(room)
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

    override fun searchAccommodations(
        pageable: Pageable,
        query: String?,
        checkinDate: LocalDate,
        checkoutDate: LocalDate
    ): Page<Room> {
        val roomWhereClause = BooleanBuilder()

        query?.let {
            roomWhereClause
                .and(accommodation.address.containsIgnoreCase("$query"))
        }

        roomWhereClause
            .and(
                room.id.notIn(
                    JPAExpressions.select(room.id)
                        .from(room)
                        .leftJoin(reservation).on(room.id.eq(reservation.room.id))
                        .where(
                            reservation.checkinDate.between(
                                checkinDate.atTime(LocalTime.MIN),
                                checkoutDate.atTime(LocalTime.NOON)
                            )
                                .or(
                                    reservation.checkoutDate.between(
                                        checkinDate.atTime(LocalTime.MIN),
                                        checkoutDate.atTime(LocalTime.NOON)
                                    )
                                )
                        )
                )
            )

        val roomContents: MutableList<Room>
        val totalCount: Long
        try {
            roomContents = queryFactory
                .selectFrom(room)
                .where(roomWhereClause)
                .offset(pageable.offset)
                .orderBy(*getOrderSpecifier(pageable, room))
                .fetch()

            totalCount = queryFactory
                .select(room.count())
                .from(room)
                .where(roomWhereClause)
                .fetchOne() ?: 0L
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(
                "잘못된 sort 인자 ${
                    pageable.sort.toList().map { it.property }.joinToString(",")
                }를 넣으셨습니다."
            )
        } catch (e: Exception) {
            throw IllegalArgumentException("plz exception check")
        }

        return PageImpl(roomContents, pageable, totalCount)
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