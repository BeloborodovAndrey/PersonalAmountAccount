package com.web.personalaccountwithcurrency.util;

import com.web.personalaccountwithcurrency.repository.entity.Rates;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class CurrencyData {

    public static final List<Rates> TEST_CURRENCY_DATA = Stream.of(
                    new Rates(1L, "USD", BigDecimal.valueOf(60)),
                    new Rates(2L, "MXN", BigDecimal.valueOf(3)),
                    new Rates(3L, "EUR", BigDecimal.valueOf(70)),
                    new Rates(4L, "AMD", BigDecimal.valueOf(7))
            )
            .collect(Collectors.toList());

}
