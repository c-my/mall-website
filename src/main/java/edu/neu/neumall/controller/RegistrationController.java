package edu.neu.neumall.controller;


import edu.neu.neumall.entity.User;
import edu.neu.neumall.form.RegistrationForm;
import edu.neu.neumall.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationController(@Qualifier("userRepository") UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @GetMapping
//    public String registrationPage() {
//        return "pages/register.html";
//    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        User user = userRepository.findByPhone(form.getPhone());
        if (user != null) {
            return "{\"success\":\"false\"}";
        }
        userRepository.save(form.toUser(bCryptPasswordEncoder));
        return "{\"success\":\"true\"}";
    }
}


