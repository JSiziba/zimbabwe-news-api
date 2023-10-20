package com.siziba.zim_news.zim_news;

import com.siziba.zim_news.zim_news.entity.ApplicationUser;
import com.siziba.zim_news.zim_news.repository.ApplicationUserRepository;
import com.siziba.zim_news.zim_news.type.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ZimNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZimNewsApplication.class, args);
	}

	@Bean
	public CommandLineRunner createAdminUser(ApplicationUserRepository applicationUserRepository) {
		return (args) -> {
			if (applicationUserRepository.count() == 0) {
				log.info("Creating admin user");
				ApplicationUser admin = ApplicationUser.builder()
						.firstName("Super")
						.lastName("Admin")
						.role(Role.SUPER_ADMIN)
						.email("siziba.uz@outlook.com")
						.password("$2a$10$lJ.2a99Yu9HLzqG1S9B.TeET1tPk1B3M5.S.7H7ZrVcgo5FmU1ljq")
						.build();
				applicationUserRepository.save(admin);
				log.info("Admin user created");
			}
		};
	}

}
