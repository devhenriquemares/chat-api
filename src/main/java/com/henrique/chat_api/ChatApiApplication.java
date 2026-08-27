package com.henrique.chat_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class ChatApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatApiApplication.class, args);
	}

}
