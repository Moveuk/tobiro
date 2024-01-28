package com.sparta.tobiro.api.reservation.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import jakarta.validation.ReportAsSingleViolation
import kotlin.reflect.KClass

@Constraint(validatedBy = [ValidContentValidator::class])
@ReportAsSingleViolation
@Retention(AnnotationRetention.RUNTIME)
@Target(allowedTargets = [AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER])
annotation class ValidContent(
        val message: String = "Content는 최소 10자 이상, 1000자 이하로 작성되어야 합니다.",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Payload>> = []

)
