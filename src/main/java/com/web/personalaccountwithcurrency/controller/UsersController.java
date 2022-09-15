package com.web.personalaccountwithcurrency.controller;

import com.web.personalaccountwithcurrency.config.CustomAuthenticationManager;
import com.web.personalaccountwithcurrency.repository.entity.User;
import com.web.personalaccountwithcurrency.service.UserService;
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
    public String getUser(@RequestParam("token") String token) {
        try {
            if (userService.isAuthorizedUser(token)) {
                return "home";
            }
            return "index";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "index";
        }
    }

    @GetMapping("/homePage")
    public String getHome() {
        return "home";
    }

    @ResponseBody
    @GetMapping("/currentAmount")
    public String getCurrentAmount() {
        return userService.getCurrentAmount();
    }

    @ResponseBody
    @PostMapping("/signIn")
    public User signUser(@RequestParam("login") String login, @RequestParam("password") String password) {
        return userService.loadUserByUsernameAndPassword(login, password);
    }
}
