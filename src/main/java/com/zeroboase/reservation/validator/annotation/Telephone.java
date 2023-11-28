package com.zeroboase.reservation.validator.annotation;

import com.zeroboase.reservation.type.ValidationMessage;
import com.zeroboase.reservation.validator.TelephoneValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelephoneValidator.class)
public @interface Telephone {

    String message() default ValidationMessage.INVALID_TEL_FORMAT;

    Class[] groups() default {};

    Class[] payload() default {};
}
