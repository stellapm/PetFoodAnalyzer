package com.example.petfoodanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PetFoodAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetFoodAnalyzerApplication.class, args);
    }

}
