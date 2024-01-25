package com.sparta.tobiro.domain.reservation.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidRatingValidator: ConstraintValidator<ValidRating, Int> {
    override fun isValid(value: Int?, context: ConstraintValidatorContext?): Boolean {
        val isValid = value != null && value.toString().matches(Regex("^[1-5]*$")) && value.toString().length in 1..1
        if (!isValid) {
            context?.disableDefaultConstraintViolation()
            context?.buildConstraintViolationWithTemplate("Rating은 1점~5점 중에 줄 수 있습니다.")
                    ?.addConstraintViolation()
        }
        return isValid
    }
}

