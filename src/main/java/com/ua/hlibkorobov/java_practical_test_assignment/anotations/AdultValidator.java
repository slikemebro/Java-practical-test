package com.ua.hlibkorobov.java_practical_test_assignment.anotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@Log4j2
public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Value("${adult.minimum.age}")
    private int minimumAge = 18;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate now = LocalDate.now();
        Period period = Period.between(value, now);
        log.info("validation for age: {}", period.getYears());
        return period.getYears() >= minimumAge;
    }

}
