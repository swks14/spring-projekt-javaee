package net.projekt.springboot.web;

import net.projekt.springboot.config.Email.FeedbackController;
import net.projekt.springboot.service.UserServiceImpl;
import net.projekt.springboot.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    @Autowired
    FeedbackController feedbackController;

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
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto registrationDto) {
        userService.save(registrationDto);
        feedbackController.Sendmail(registrationDto.getEmail());
        return "redirect:/registration?success";
    }
}
