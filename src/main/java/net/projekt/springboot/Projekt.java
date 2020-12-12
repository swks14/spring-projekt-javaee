package net.projekt.springboot;

import net.projekt.springboot.model.Application;
import net.projekt.springboot.repository.ApplicationRepository;
import net.projekt.springboot.repository.UserRepository;
import net.projekt.springboot.service.UserServiceImpl;
import net.projekt.springboot.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class Projekt {
	@Autowired
	UserServiceImpl userService;
	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Projekt.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void Init() {
		String line;
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader("src/main/java/net/projekt/springboot/init/ProjectVIIIData/Users1.csv"));
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",", 7);
				userService.save(new UserRegistrationDto(split[1], split[2], split[3], split[4], split[5], split[6]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//        try {
//            br = new BufferedReader(new FileReader("src/main/java/net/projekt/springboot/init/ProjectVIIIData/Persons.csv"));
//            while ((line = br.readLine()) != null) {
//                String[] split = line.split(",", 7);
//                userService.save(new UserRegistrationDto(split[1], split[2], split[3], split[4], split[5], split[6]));
//            }
//        } catch(IOException  e){
//            e.printStackTrace();
//        }
		try {
			br = new BufferedReader(new FileReader("src/main/java/net/projekt/springboot/init/ProjectVIIIData/Domains.csv"));
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",", 4);
				Application application = new Application(split[1], split[2]);
				applicationRepository.save(application);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
