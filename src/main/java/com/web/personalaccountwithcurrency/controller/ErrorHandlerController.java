package com.web.personalaccountwithcurrency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
@Slf4j
public class ErrorHandlerController implements ErrorController {

    @RequestMapping("/error")
    @ExceptionHandler(value = Exception.class)
    public String getErrorPath() {
        return "index";
    }
}
