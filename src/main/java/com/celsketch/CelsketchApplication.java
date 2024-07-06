package com.celsketch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.celsketch.mapper")
public class CelsketchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CelsketchApplication.class, args);
	}
}