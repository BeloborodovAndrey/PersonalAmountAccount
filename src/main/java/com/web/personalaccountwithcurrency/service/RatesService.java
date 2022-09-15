package com.web.personalaccountwithcurrency.service;

import com.web.personalaccountwithcurrency.model.RateDto;

public interface RatesService {
    String convert(RateDto dto);
}
