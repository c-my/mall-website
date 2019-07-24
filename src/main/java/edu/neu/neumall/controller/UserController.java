package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * @param user current login user
     * @return user details
     * return user details of the current user
     */
    @GetMapping
    public User getUserDetail(@AuthenticationPrincipal User user) {
        if (user == null) {
            return new User();
        }
        return user;
    }

    /**
     * @param user current login user
     * @return all user list if current user is ADMIN
     */
    @GetMapping("all")
    public @ResponseBody
    Iterable<User> getAllUsers(@AuthenticationPrincipal User user) {

        if (!user.getRole().equals(User.UserRole.ADMIN)) {
            return new ArrayList<>();
        }
        return userRepository.findAll();

    }

    @PostMapping
    public @ResponseBody
    String addNewUser(@RequestBody User user) {
        userRepository.save(user);
        return "user added";
    }

    @DeleteMapping
    public String removeUser(long userID) {
        userRepository.deleteById(userID);
        return "\"success\":true";
    }
}
