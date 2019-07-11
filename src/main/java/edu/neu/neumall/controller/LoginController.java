package edu.neu.neumall.controller;


import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createNewUser(@RequestBody User user) {
        User userExists = userService.findUserByName(user.getUserName());
        if (userExists != null) {

        } else {
            userService.saveUser(user);
        }
        return "html/userlist.html";
    }
}
