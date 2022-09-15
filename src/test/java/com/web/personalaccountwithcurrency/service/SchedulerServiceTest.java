package com.web.personalaccountwithcurrency.service;

import com.web.personalaccountwithcurrency.repository.RatesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static com.web.personalaccountwithcurrency.util.CurrencyData.TEST_CURRENCY_DATA;
import static org.mockito.Mockito.when;

@SpringBootTest
class SchedulerServiceTest {

    @Mock
    private RatesRepository ratesRepository;

    @Test
    void updateRates() {
        SchedulerService schedulerService = new SchedulerService(ratesRepository);
        when(ratesRepository.findAll()).thenReturn(TEST_CURRENCY_DATA);
        schedulerService.updateRates();
        Mockito.verify(ratesRepository, Mockito.times(4))
                .save(Mockito.any());
    }
}