package edu.neu.neumall.controller;


import edu.neu.neumall.repository.UserRepository;
import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationController(@Qualifier("userRepository") UserRepository userRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  UserService userService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @PostMapping
    public String processRegistration(@Valid UserService.UserRegistrationForm form,
                                      Errors errors) {
        if (errors.hasErrors()) {
            return "{\"success\":\"false\"}";
        }
        var user = userRepository.findByPhone(form.getPhone());
        if (user.isEmpty()) {
            userRepository.save(userService.toUser(form, bCryptPasswordEncoder));
            return "{\"success\":\"true\"}";
        } else {
            return "{\"success\":\"false\"}";
        }
    }
}


