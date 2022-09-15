package com.web.personalaccountwithcurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableTransactionManagement
@EnableJpaRepositories
@EnableScheduling
public class PersonalAccountWithCurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalAccountWithCurrencyApplication.class, args);
    }

}
