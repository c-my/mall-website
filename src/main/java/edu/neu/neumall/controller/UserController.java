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
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("add")
    public @ResponseBody String addNewUser(@RequestParam String name,
                                           @RequestParam String passwd ,
                                           @RequestParam String phone,
                                           @RequestParam String email,
                                           @RequestParam String question,
                                           @RequestParam String answer){
        User u = new User();
        u.setAnswer(answer);
        u.setEmail(email);
        u.setPhone(phone);
        u.setPassword(passwd);
        u.setQuestion(question);
        u.setUserName(name);
        userRepository.save(u);
        return "user added";

    }
}
