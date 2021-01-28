package net.projekt.springboot.web;

import net.projekt.springboot.model.Application;
import net.projekt.springboot.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
	@Autowired
	ApplicationRepository applicationRepository;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/guest/applications")
	public String apps(Model model) {
		List<Application> apps = applicationRepository.findAll();
		model.addAttribute("applications", apps);
		return "GuestApplications";

	}

}
