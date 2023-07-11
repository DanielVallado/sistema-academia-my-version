package edu.uady.sistemapagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync
public class SistemaPagosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaPagosApplication.class, args);
    }

}
