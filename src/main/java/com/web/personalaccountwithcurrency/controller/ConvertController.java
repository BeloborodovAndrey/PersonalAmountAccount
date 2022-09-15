package com.web.personalaccountwithcurrency.controller;

import com.web.personalaccountwithcurrency.model.RateDto;
import com.web.personalaccountwithcurrency.service.RatesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contoller for rates requests
 */
@RestController
@AllArgsConstructor
public class ConvertController {

    private RatesService ratesServiceImpl;

    @PostMapping(value = "/convert")
    public ResponseEntity<String> convert(@RequestBody RateDto dto) {
        return new ResponseEntity<>(ratesServiceImpl.convert(dto), HttpStatus.OK);
    }
}
