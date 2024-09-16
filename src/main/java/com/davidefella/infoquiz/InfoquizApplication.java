package com.davidefella.infoquiz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class InfoquizApplication {

	public static void main(String[] args) {
		log.info("*** APPLICATION STARTUP ***");

		SpringApplication.run(InfoquizApplication.class, args);
	}
}