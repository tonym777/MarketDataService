package com.mt.marketdata;

import com.mt.marketdata.engine.Engine;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


@SpringBootApplication
public class PricingEngine {

    @PostConstruct
    public void postConstruct() {
        String configFile = "app.properties";
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(configFile));
            new Thread(new Engine(props)).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(PricingEngine.class, args);
    }
}