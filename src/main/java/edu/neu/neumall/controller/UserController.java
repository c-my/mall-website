package edu.neu.neumall.controller;

import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * set a user non locked
     *
     * @return status
     */
    @PatchMapping
    public String unlockUser(@RequestParam("id") long userID) {
        var userExist = userService.findUserByID(userID);
        if (userExist.isEmpty()) {
            return "\"success\":false";
        }
        var user = userExist.get();
        user.setUserLocked(false);
        userService.saveUser(user);
        return "\"success\":true";
    }

    @DeleteMapping
    public String removeUser(@RequestParam("id") long userID) {
        var userExist = userService.findUserByID(userID);
        if (userExist.isEmpty()) {
            return "\"success\":false";
        }
        var user = userExist.get();
        user.setUserLocked(true);
        userService.saveUser(user);
        return "\"success\":true";
    }
}
