package com.multicamp.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan(basePackages = {"com.multicamp"})
//@MapperScan(basePackages = {"com.multicamp.mapper"})
@EnableJpaRepositories(basePackages = {"com.multicamp.persistence"})
@EntityScan(basePackages = {"com.multicamp.domain"})
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class SpringbootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoApplication.class, args);
	}

}
