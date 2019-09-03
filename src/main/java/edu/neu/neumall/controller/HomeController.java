package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String myHomePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "homepage";
    }

//    @GetMapping("/{userID}")
//    public String homePage(@PathVariable("userID") long userID, Model model) {
//        var user = userService.findUserByID(userID);
//        if (user.isEmpty()) {
//            return "error";
//        }
//        model.addAttribute("user", user.get());
//        return "homepage.html";
//    }

    @GetMapping("/profile")
    public String profilePage(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        return "profile.html";
    }

    @GetMapping("/shipping")
    public String shippingPage(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        return "shipping.html";
    }

    @GetMapping("/change")
    public String changePage(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        return "change.html";
    }
}
