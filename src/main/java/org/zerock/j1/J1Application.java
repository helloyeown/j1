package org.zerock.j1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class J1Application {

	public static void main(String[] args) {
		SpringApplication.run(J1Application.class, args);
	}

}
