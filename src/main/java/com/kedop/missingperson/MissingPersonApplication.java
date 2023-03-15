package com.kedop.missingperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MissingPersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissingPersonApplication.class, args);
    }

    //TODO handle later https://www.youtube.com/watch?v=AzSY0UlsrWQ
//    @Bean
//    public Function<String, String> toUpper() {
//        return str -> {
//            System.out.println(str);
//            return str.toUpperCase(Locale.ROOT);
//        };
//    }
}
