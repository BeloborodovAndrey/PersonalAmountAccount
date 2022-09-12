package com.web.personalaccountwithcurrency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * IndexController
 */
@Controller
public class IndexController {

    /**
     * Index endpoint to show the index page
     * @return view name
     */
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }
}
