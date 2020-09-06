package eu.mnrdesign.matned.final_project.validation;

import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsNumericValidator implements ConstraintValidator<IsNumeric, String> {

    @Override
    public void initialize(IsNumeric constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try{
            long val = Long.parseLong(value);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
}
