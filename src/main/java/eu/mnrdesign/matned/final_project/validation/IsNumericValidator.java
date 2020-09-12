package eu.mnrdesign.matned.final_project.validation;

import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class IsNumericValidator implements ConstraintValidator<IsNumeric, BigDecimal> {

    @Override
    public void initialize(IsNumeric constraintAnnotation) {

    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        try{
            BigDecimal b = new BigDecimal(0).add(value);
            return true;
        }catch (Exception ignored){
            return false;
        }
    }
}
