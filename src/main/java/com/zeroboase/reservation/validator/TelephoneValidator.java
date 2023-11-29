package com.zeroboase.reservation.validator;

import com.zeroboase.reservation.validator.annotation.Telephone;
import com.zeroboase.reservation.validator.constant.RegexPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.matches(RegexPattern.TEL);
    }
}
