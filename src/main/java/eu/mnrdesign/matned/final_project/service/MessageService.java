package eu.mnrdesign.matned.final_project.service;

import eu.mnrdesign.matned.final_project.dto.PasswordResetDTO;
import eu.mnrdesign.matned.final_project.message.Message;
import eu.mnrdesign.matned.final_project.model.PasswordReset;
import eu.mnrdesign.matned.final_project.repository.ResetPasswordRepository;
import eu.mnrdesign.matned.final_project.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Service
public class MessageService {

    private final JavaMailSender javaMailSender;
    private final ResetPasswordRepository resetPasswordRepository;
    private final UserRepository userRepository;

    public MessageService(JavaMailSender javaMailSender, ResetPasswordRepository resetPasswordRepository, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.resetPasswordRepository = resetPasswordRepository;
        this.userRepository = userRepository;
    }

    public void send(Message message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(message.getEmail());
        simpleMailMessage.setTo("javawro27@gmail.com");
        simpleMailMessage.setSubject(message.getSubject());
        simpleMailMessage.setText(message.getMessage());
        simpleMailMessage.setSentDate(new Date());
        javaMailSender.send(simpleMailMessage);

    }

    public void sendPasswordReset(PasswordResetDTO dto) {
        saveResetPasswordToken(dto);
        SimpleMailMessage simpleMailMessage = generateMailMessage(dto);
        javaMailSender.send(simpleMailMessage);
    }





//    generating message

    private SimpleMailMessage generateMailMessage(PasswordResetDTO dto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("javawro27@gmail.com");
        simpleMailMessage.setTo(dto.getLogin());
        simpleMailMessage.setSubject("Password reset token");
        simpleMailMessage.setText(Objects.requireNonNull(generateMessage(dto)));
        simpleMailMessage.setSentDate(new Date());
        return simpleMailMessage;
    }

    private String generateMessage(PasswordResetDTO dto) {
        PasswordReset passwordReset = saveResetPasswordToken(dto);
        StringBuilder message = new StringBuilder();
        String link = ""+passwordReset.getToken();
        message.append("Hello").append(System.getProperty("line.separator"))
                .append("Here is the password reset token.").append(System.getProperty("line.separator"))
                .append("Click the link below:").append(System.getProperty("line.separator"))
                .append(link).append(System.getProperty("line.separator"))
                .append("Bye").append(System.getProperty("line.separator"));

        return String.valueOf(message);
    }

    private PasswordReset saveResetPasswordToken(PasswordResetDTO dto) {
        PasswordReset passwordReset = new PasswordReset(
                LocalDateTime.now().plusMinutes(10),
                generateToken(),
                userRepository.findByLogin(dto.getLogin()).orElseThrow(()->new RuntimeException("No such user"))
        );
        resetPasswordRepository.save(passwordReset);
        return passwordReset;
    }

    private String generateToken(){
        return new RandomString(20).nextString();
    }
}
