package com.spordee.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan
@EnableAutoConfiguration
@Component
//@SpringBootApplication
public class MessageApp {

	public static void main(String[] args) {
		SpringApplication.run(MessageApp.class, args);
	}

}
