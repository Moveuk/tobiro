package com.sparta.tobiro.api.reservation.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidContentValidator: ConstraintValidator<ValidContent, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        val isValid = value != null &&  value.length in 10..1000
        if (!isValid) {
            context?.disableDefaultConstraintViolation()
            context?.buildConstraintViolationWithTemplate("Content는 최소 10자 이상, 1000자 이하로 작성되어야 합니다.")
                    ?.addConstraintViolation()
        }
        return isValid
    }
}
