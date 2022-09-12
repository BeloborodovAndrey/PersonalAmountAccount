package com.web.personalaccountwithcurrency.controller;

import com.web.personalaccountwithcurrency.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getUser() {
        return "home";
    }

    @PostMapping("/signIn")
    public Boolean signUser(@RequestParam String login, @RequestParam String password) {
        return userService.loadUserByUsernameAndPassword(login, password).isEnabled();
    }
}
