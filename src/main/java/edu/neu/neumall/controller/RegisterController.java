package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String registerPage(Model model) {
        model.addAttribute("registrationForm", new UserService.UserRegistrationForm());
        return "register";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("registrationForm") UserService.UserRegistrationForm registrationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", registrationForm);
            System.out.println("errors");
            return "register";
        }
        User newUser = userService.toUser(registrationForm, bCryptPasswordEncoder);
        userService.saveUser(newUser);
        // TODO: 2019/8/25 duplication check
        return "redirect:/login";
    }
}
