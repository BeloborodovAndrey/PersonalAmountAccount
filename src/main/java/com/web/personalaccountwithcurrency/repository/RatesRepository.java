package com.web.personalaccountwithcurrency.repository;


import com.web.personalaccountwithcurrency.repository.entity.Rates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatesRepository extends JpaRepository<Rates, Long> {
    Rates findByCurrency(String currency);
}
