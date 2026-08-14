package com.jurisai.validation.validator;

import com.jurisai.validation.annotations.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

// validate format of date and date in past
public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public void initialize(ValidDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (date == null || date.trim().isEmpty()) {
            return true;
        }

        try {
            LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);

            if (localDate.isAfter(LocalDate.now())) {
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
