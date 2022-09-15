package com.web.personalaccountwithcurrency.service;

import com.web.personalaccountwithcurrency.model.RateDto;
import com.web.personalaccountwithcurrency.repository.RatesRepository;
import com.web.personalaccountwithcurrency.repository.entity.Rates;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Andrey
 * service for converting rates
 */
@Service
@AllArgsConstructor
@Slf4j
public class RatesServiceImpl implements RatesService {

    private final RatesRepository ratesRepository;
    /**
     * conversation currency
     *
     * @param dto with rates data
     * @return String with amount value
     */
    @Override
    public String convert(RateDto dto) {
        try {
            String currency = dto.getCurrency();
            BigDecimal amountRate = new BigDecimal(dto.getAmount());
            Rates rates = ratesRepository.findByCurrency(currency);
            if (rates != null) {
                return String.valueOf(rates.getAmount().multiply(amountRate).setScale(2, RoundingMode.HALF_UP));
            }
            return "";
        } catch (ClassCastException | NumberFormatException e) {
            log.error("problems with rates converting: {}", e.getMessage());
            return "parsing error";
        }
    }
}
