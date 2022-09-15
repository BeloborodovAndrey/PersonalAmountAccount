package com.web.personalaccountwithcurrency.service;

import com.web.personalaccountwithcurrency.repository.RatesRepository;
import com.web.personalaccountwithcurrency.repository.entity.Rates;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

/**
 * service for currency rates updating
 */
@Component
public class SchedulerService {

    private final RatesRepository ratesRepository;

    public SchedulerService(RatesRepository ratesRepository) {
        this.ratesRepository = ratesRepository;
    }

    @Scheduled(fixedRate = 20000, initialDelay = 40000)
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateRates() {
        List<Rates> ratesList = ratesRepository.findAll();
        if (!ratesList.isEmpty()) {
            ratesList.stream()
                    .peek(rate -> {
                        BigDecimal curAmount = rate.getAmount();
                        BigDecimal newAmount = transformAmount(curAmount);
                        rate.setAmount(newAmount);
                    })
                    .forEach(
                            ratesRepository::save
                    );
        }
    }

    private BigDecimal transformAmount(BigDecimal curAmount) {
        BigDecimal prcValue = curAmount
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(0.5));
        int newValue = new Random().nextInt(prcValue.negate().intValue(), prcValue.intValue());
        return BigDecimal.valueOf(newValue).add(curAmount);
    }
}
