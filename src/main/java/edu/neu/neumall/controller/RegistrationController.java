package edu.neu.neumall.controller;


import edu.neu.neumall.repository.UserRepository;
import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String processRegistration(UserService.UserRegistrationForm form) {
        var user = userRepository.findByPhone(form.getPhone());
        if (user.isEmpty()) {
            userRepository.save(userService.toUser(form, bCryptPasswordEncoder));
            return "{\"success\":\"true\"}";
        } else {
            return "{\"success\":\"false\"}";
        }
    }
}


