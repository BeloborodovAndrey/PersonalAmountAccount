package com.web.personalaccountwithcurrency.service;

import com.web.personalaccountwithcurrency.model.RateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RatesServiceImplTest {

    @Autowired
    private RatesServiceImpl ratesServiceImpl;

    @Test
    void convert_test() {
        RateDto rateDto = new RateDto("USD", "60");
        String actualRes = ratesServiceImpl.convert(rateDto);

        assertNotNull(actualRes);
        assertEquals("3600.00", actualRes);
    }

    @Test
    void convertEur_test() {
        RateDto rateDto = new RateDto("EUR", "70");
        String actualRes = ratesServiceImpl.convert(rateDto);

        assertNotNull(actualRes);
        assertEquals("4900.00", actualRes);
    }

    @Test
    void convertFail_test() {
        RateDto rateDto = new RateDto("USD", "fwefsge");
        String actualRes = ratesServiceImpl.convert(rateDto);

        assertNotNull(actualRes);
        assertEquals("parsing error", actualRes);
    }

}