package com.web.personalaccountwithcurrency.controller;

import com.web.personalaccountwithcurrency.repository.entity.User;
import com.web.personalaccountwithcurrency.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @ResponseBody
    @PutMapping("/registration")
    public Boolean addUser(@RequestParam("login") String login, @RequestParam("password") String password) {
        return userService.saveUser(login, password);
    }

    @GetMapping("/userLogout")
    public String logout() {
        userService.logout();
        return "index";
    }
}
