package eu.mnrdesign.matned.final_project.validation;

import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistValidator implements ConstraintValidator<EmailExist, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(EmailExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.userWithEmailExists(value);
    }
}
