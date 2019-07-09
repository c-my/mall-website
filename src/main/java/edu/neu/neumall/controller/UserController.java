package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("add")
    public @ResponseBody
    String addNewUser(@RequestBody User user) {
        User u = user;
        userRepository.save(u);
        return "user added";
    }
}
