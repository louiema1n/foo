package com.lm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lm.mapper")
public class FooApplication {

	public static void main(String[] args) {
		SpringApplication.run(FooApplication.class, args);
	}
}
