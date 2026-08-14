package com.jurisai.validation.annotations;

import com.jurisai.validation.validator.ValidDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Ngày không hợp lệ. Yêu cầu định dạng dd-MM-yyyy";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
