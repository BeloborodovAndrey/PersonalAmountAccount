package com.web.personalaccountwithcurrency.controller;

import com.web.personalaccountwithcurrency.repository.entity.User;
import com.web.personalaccountwithcurrency.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UsersController {

    private final UserService userService;


    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login", "/index"})
    public String getLoginPage() {
        return "index";
    }


    @GetMapping("/home")
    @ResponseBody
    public String getUser(@RequestParam("token") String token) {
        if (userService.isAuthorizedUser(token)) {
            return "true";
        }
        return "true";
    }

    @GetMapping("/homePage")
    public String getHomePage() {
        return "account";
    }

    @ResponseBody
    @GetMapping("/currentUser")
    public User getCurrentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication);
    }

    @ResponseBody
    @PostMapping("/signIn")
    public User signUser(@RequestParam("login") String login, @RequestParam("password") String password) {
        return userService.loadUserByUsernameAndPassword(login, password);
    }
}
