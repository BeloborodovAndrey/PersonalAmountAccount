package com.web.personalaccountwithcurrency.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * entity for currency rates saving
 */
@Entity
@Table(name = "t_rates")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Setter
@Getter
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private BigDecimal amount;

}
