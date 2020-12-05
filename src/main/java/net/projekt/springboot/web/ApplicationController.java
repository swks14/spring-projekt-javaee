package net.projekt.springboot.web;

import lombok.Data;
import net.projekt.springboot.model.Application;
import net.projekt.springboot.repository.ApplicationRepository;
import net.projekt.springboot.repository.UserRepository;
import net.projekt.springboot.service.ApplicationServiceImpl;
import net.projekt.springboot.web.dto.ApplicationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Data
@Controller
public class ApplicationController {
    private ApplicationServiceImpl applicationService;
    private List<Application> applications = new ArrayList<>();
    private ApplicationRepository applicationRepository;
    private UserRepository userRepository;

    public ApplicationController(ApplicationServiceImpl applicationService, ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/application")
    public String application(Model model, Principal principal) {

        applications = applicationRepository.findAllByUserIdContains(userRepository.findByEmail(principal.getName()));
        model.addAttribute("applications", applications);
        return "Applications";
    }


    @GetMapping("/application/add")
    public String AddingApplication(Model model) {
        model.addAttribute("application", new Application());
        return "ApplicationForm";
    }

    @ModelAttribute("application")
    public ApplicationDto applicationDto() {
        return new ApplicationDto();
    }

    @PostMapping("/application/add")
    public String AddApplication(@ModelAttribute("application") ApplicationDto applicationDto, Principal principal) {
        Application application = new Application(applicationDto.getName(), applicationDto.getDomain());
        application.getUserId().add(userRepository.findByEmail(principal.getName()));
        applicationService.save(application);
        return "redirect:/application";
    }

    @GetMapping("/application/delete")
    public String DeletingApplication() {
        return "ApplicationDeleteForm";
    }

    @RequestMapping(value = "/application/delete", method = RequestMethod.POST)
    public String DeleteApplication(@RequestParam Long id) {
        applicationService.deleteById(id);
        return "redirect:/application";
    }

    @GetMapping("/application/update")
    public String UpdateingApplication(Model model) {
        model.addAttribute("application", new Application());
        return "ApplicationUpdateForm";
    }

    @RequestMapping(value = "/application/update", method = RequestMethod.POST)
    public String updateApplication(@ModelAttribute("application") ApplicationDto NewApplication, @RequestParam Long id) {
        applicationService.findById(id)
                .map(application -> {
                    application.setName(NewApplication.getName());
                    application.setDomain(NewApplication.getDomain());
                    applicationService.save(application);
                    return "redirect:/application";
                })
                .orElseGet(() -> "redirect:/application");
        return "redirect:/application";
    }
}
