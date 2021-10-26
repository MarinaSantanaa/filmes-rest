package br.com.filmes.spring.filmesrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FilmesRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmesRestApplication.class, args);
    }
}
