package eu.mnrdesign.matned.final_project.repository;

import eu.mnrdesign.matned.final_project.dto.PasswordResetDTO;
import eu.mnrdesign.matned.final_project.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<PasswordReset, Long> {

    @Query("select pr from PasswordReset pr where pr.token = ?1")
    Optional<PasswordReset> findByToken(String token);
}
