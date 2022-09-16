package com.web.personalaccountwithcurrency.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * controller for returning web pages
 */
@Controller
public class IndexController {

    @GetMapping({"/", "/login", "/index"})
    public String getLoginPage() {
        return "index";
    }

}
