package com.fittoo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FitTooApplication {

    @Bean
    JPAQueryFactory JPAQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

    public static void main(String[] args) {
        SpringApplication.run(FitTooApplication.class, args);
    }

}
