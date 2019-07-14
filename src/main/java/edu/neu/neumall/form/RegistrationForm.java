package edu.neu.neumall.form;

import edu.neu.neumall.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String nickname;
    private String password;
    private String phone;
    private String email;
    private String question;
    private String answer;
    private String role;


    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        user.setNickName(nickname);
        user.setPhone(phone);
        user.setEmail(email);
        user.setQuestion(question);
        user.setAnswer(answer);
        try {
            user.setRole(User.UserRole.valueOf(role));
        } catch (IllegalArgumentException e) {
            user.setRole(User.UserRole.NONE);
        }

        return user;
    }
}
