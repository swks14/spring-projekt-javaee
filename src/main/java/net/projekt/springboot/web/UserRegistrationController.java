package net.projekt.springboot.web;

import net.projekt.springboot.service.UserServiceImpl;
import net.projekt.springboot.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserServiceImpl userService;

	public UserRegistrationController(UserServiceImpl userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping
	public String showRegistrationForm() {
		String line;
		BufferedReader br;

		try {
            br = new BufferedReader(new FileReader("src/main/java/net/projekt/springboot/init/ProjectVIIIData/Users1.csv"));
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",", 7);
                userService.save(new UserRegistrationDto(split[1], split[2], split[3], split[4], split[5], split[6]));
            }
        } catch(IOException e){
					e.printStackTrace();
				}
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        userService.save(registrationDto);
//		String line;
//		BufferedReader br;
//
//		try {
//			br = new BufferedReader(new FileReader("src/main/java/net/projekt/springboot/init/ProjectVIIIData/Users.csv"));
//			while ((line = br.readLine()) != null) {
//				String[] split = line.split(",", 5);
//				userService.save(new UserRegistrationDto(split[1],split[2],split[3],split[4]));
//			}} catch(IOException e){
//			e.printStackTrace();
//		}
        return "redirect:/registration?success";
    }
}
