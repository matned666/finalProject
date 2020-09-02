package eu.mnrdesign.matned.final_project.validation;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static eu.mnrdesign.matned.final_project.holder.Static.DATE_PATTERN;
import static org.junit.jupiter.api.Assertions.*;

class DateValidatorUsingDateFormatTest {

    @Test
    void isValid(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        DateValidator validator = new DateValidatorUsingDateFormat(dateFormatter);
        assertTrue(validator.isValid("2010-12-01"));
        assertFalse(validator.isValid("02/30/2019"));
    }

    @Test
    void isEmptyFieldValid(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        DateValidator validator = new DateValidatorUsingDateFormat(dateFormatter);
        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid("            \n "));
        assertFalse(validator.isValid("k"));
    }

}