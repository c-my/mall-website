package edu.neu.neumall.service;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findByNickName(name);
    }

    public void saveUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var u = userRepository.findByPhone(s);
        if (u.isEmpty()) {
            throw new UsernameNotFoundException("User '" + s + "'not fond");
        }
        return u.get();
    }

    @Data
    public static class UserRegistrationForm {
        @NotNull(message = "nickname can not be null")
        @NotBlank(message = "nickname must has a value")
        private String nickname;

        @NotNull(message = "password can not be null")
        @NotBlank(message = "password must has a value")
        private String password;

        @NotNull(message = "phone can not be null")
        @NotBlank(message = "phone must has a value")
        private String phone;

        @NotNull(message = "email can not be null")
        @NotBlank(message = "email must has a value")
        private String email;

        @NotNull(message = "question can not be null")
        @NotBlank(message = "question must has a value")
        private String question;

        @NotNull(message = "answer can not be null")
        @NotBlank(message = "answer must has a value")
        private String answer;

        @NotNull(message = "role can not be null")
        @NotBlank(message = "role must has a value")
        private String role;

    }

    public User toUser(UserRegistrationForm form, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(form.password));
        user.setNickName(form.nickname);
        user.setPhone(form.phone);
        user.setEmail(form.email);
        user.setQuestion(form.question);
        user.setAnswer(form.answer);
        try {
            user.setRole(User.UserRole.valueOf(form.role));
        } catch (IllegalArgumentException e) {
            user.setRole(User.UserRole.NONE);
        }

        return user;
    }

    Optional<User> findUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
