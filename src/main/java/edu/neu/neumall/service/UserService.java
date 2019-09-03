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

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
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
        @NotNull(message = "昵称不能为空")
        @NotBlank(message = "昵称不能为空")
        private String nickname;

        @NotNull(message = "密码不能为空")
        @NotBlank(message = "密码不能为空")
        @Size(min = 8, max = 16, message = "密码长度必须介于8-16之间")
        private String password;

        @NotNull(message = "手机号码不能为空")
        @NotBlank(message = "手机号码不能为空")
        private String phone;

        @NotNull(message = "Email不能为空")
        @NotBlank(message = "Email不能为空")
        @Email(message = "请输入有效的Email")
        private String email;

        @NotNull(message = "问题不能为空")
        @NotBlank(message = "问题不能为空")
        private String question;

        @NotNull(message = "答案不能为空")
        @NotBlank(message = "答案不能为空")
        private String answer;

        @NotNull(message = "role can not be null")
        @NotBlank(message = "role must has a value")
        private String role;
    }

    public User toUser(UserRegistrationForm form, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setPassword(form.password);
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

    public Optional<User> findUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public Optional<User> findUserByID(long id) {
        return userRepository.findById(id);
    }

    public void deleteUserByID(long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
