package eu.mnrdesign.matned.final_project.validation;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_PATTERN;

public class DateMatchesPatternValidator implements ConstraintValidator<DateMatchesPattern, String> {
    @Override
    public void initialize(DateMatchesPattern constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return new DateValidatorUsingDateFormat(DateTimeFormatter.ofPattern(DATE_PATTERN))
                .isValid(value);
    }
}
