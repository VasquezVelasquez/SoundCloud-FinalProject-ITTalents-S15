package com.example.soundcloudfinalprojectittalentss15;

import jakarta.validation.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SoundCloudFinalProjectItTalentsS15Application {

	public static void main(String[] args) {
		SpringApplication.run(SoundCloudFinalProjectItTalentsS15Application.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Bean
//	public Validation validation() {
//		return new Validation();
//	}

}
