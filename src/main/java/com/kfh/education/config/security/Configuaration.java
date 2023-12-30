package com.kfh.education.config.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuaration {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
