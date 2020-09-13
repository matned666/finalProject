package eu.mnrdesign.matned.final_project.repository;

import eu.mnrdesign.matned.final_project.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordRepository extends JpaRepository<PasswordReset, Long> {
}
