package com.ssafy.campcino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import org.mybatis.spring.annotation.MapperScan;
@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy"})
@MapperScan("com.ssafy.campcino.mapper")
public class CampCinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampCinoApplication.class, args);
	}

}
