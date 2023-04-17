package com.example.soundcloudfinalprojectittalentss15;


import com.example.soundcloudfinalprojectittalentss15.services.EmailSenderService;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
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

	@Autowired
	private EmailSenderService senderService;

	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() {
		senderService.sendMail("MomchilGalabov@outlook.com",
				"Subject",
				"Email body");
	}


}
