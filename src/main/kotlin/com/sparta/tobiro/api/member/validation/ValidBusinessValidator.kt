package com.sparta.tobiro.api.member.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidBusinessValidator : ConstraintValidator<ValidBusinessNumber, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {

        val isValid = value != null && value.matches(Regex("^[0-9-]*$")) && value.length in 12..12

        if (!isValid) {
            context?.disableDefaultConstraintViolation()
            context?.buildConstraintViolationWithTemplate("BusinessNumber는 -포함 12자리입니다 . 숫자(0~9)로 구성 되어야 합니다.")
                ?.addConstraintViolation()
        }
        return isValid
    }
}