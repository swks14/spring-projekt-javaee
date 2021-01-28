package net.projekt.springboot.web;

import net.projekt.springboot.Exceptions.NotFoundException;
import net.projekt.springboot.model.Application;
import net.projekt.springboot.model.User;
import net.projekt.springboot.repository.ApplicationRepository;
import net.projekt.springboot.repository.UserRepository;
import net.projekt.springboot.service.ApplicationServiceImpl;
import net.projekt.springboot.web.dto.ApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {
    @Autowired
    private ApplicationServiceImpl applicationService;
    private List<Application> applications = new ArrayList<>();
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserRepository userRepository;
//    public ApplicationController(ApplicationServiceImpl applicationService, ApplicationRepository applicationRepository, UserRepository userRepository) {
//        this.applicationService = applicationService;
//        this.applicationRepository = applicationRepository;
//        this.userRepository = userRepository;
//    }


    @GetMapping("/application")
    public String application(Model model, Principal principal) {
        User userToFind = userRepository.findByEmail(principal.getName());
        applications = applicationRepository.findAllByUserIdContains(userToFind);
        model.addAttribute("applications", applications);
        return "Applications";
    }

    @GetMapping("/application/all")
    public String allApplication(@Valid Model model) {
        applications = applicationRepository.findAllByOrderByIdAsc();
        model.addAttribute("applications", applications);
        return "AllApplications";
    }


    @GetMapping("/application/add")
    public String AddingApplication(@Valid Model model) {
        model.addAttribute("application", new Application());
        return "ApplicationForm";
    }

    @ModelAttribute("application")
    public ApplicationDto applicationDto() {
        return new ApplicationDto();
    }

    @PostMapping("/application/add")
    public String AddApplication(@ModelAttribute("application") @Valid ApplicationDto applicationDto, Principal principal) {
        Application application = new Application(applicationDto.getName(), applicationDto.getDomain());
        application.getUserId().add(userRepository.findByEmail(principal.getName()));
        applicationRepository.save(application);
        return "redirect:/application";
    }

    @GetMapping("/application/delete")
    public String DeletingApplication() {
        return "ApplicationDeleteForm";
    }

    @RequestMapping(value = "/application/delete", method = RequestMethod.POST)
    public String DeleteApplication(@RequestParam Long id) {
        Application app = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        app.getUserId().clear();
        applicationRepository.deleteById(id);
        return "redirect:/application";
    }

    @GetMapping("/application/update")
    public String UpdatingApplication(@Valid Model model) {
        model.addAttribute("application", new Application());
        return "ApplicationUpdateForm";
    }


    @RequestMapping(value = "/application/update", method = RequestMethod.POST)
    public String updateApplication(@ModelAttribute("application") @Valid ApplicationDto NewApplication) {
        applicationService.findById(NewApplication.getId())
                .map(application -> {
                    application.setName(NewApplication.getName());
                    application.setDomain(NewApplication.getDomain());
                    System.out.println("cos dziala");
                    System.out.println(NewApplication.getId());
                    System.out.println("cos dziala");
                    applicationService.save(application);
                    return "redirect:/application";
                })
                .orElseGet(() -> "redirect:/application");
        return "redirect:/application";
    }

    @GetMapping("/application/update/{id}")
    public String UpdatingApplicationId(@Valid Model model, @PathVariable Long id) {
        Application application = new Application();
        application.setId(id);
        model.addAttribute("application", application);
        return "ApplicationIdUpdateForm";
    }

    @GetMapping("/application/add/{id}")
    public String AddingApplicationId(@PathVariable Long id, Principal principal) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        application.getUserId().add(userRepository.findByEmail(principal.getName()));
        applicationRepository.save(application);
        return "redirect:/application";
    }

    @RequestMapping(value = "/application/idupdate", method = RequestMethod.POST)
    public String updateApplicationById(@ModelAttribute("application") @Valid ApplicationDto NewApplication) {
        applicationService.findById(NewApplication.getId())
                .map(application -> {
                    application.setName(NewApplication.getName());
                    application.setDomain(NewApplication.getDomain());
                    applicationService.save(application);
                    return "redirect:/application";
                })
                .orElseGet(() -> "redirect:/application");
        return "redirect:/application";
    }

    @GetMapping("/application/delete/{id}")
    public String DeleteApplicationId(@PathVariable Long id) {
        Application app = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        app.getUserId().clear();
        applicationRepository.deleteById(id);
        return "redirect:/application";
    }

    @PostMapping("/apps/tocsv")
    public String appsToCsv() throws IOException {
        String path = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "apps.csv";
        FileWriter f = new FileWriter(path);
        for (Application application : applications) {
            f.write("\n" + application.toString());
        }
        f.close();
        return "redirect:/application";
    }

    @GetMapping("/application/{id}/users")
    public String ShowAppUsers(@PathVariable Long id, Model model) {
        Application app = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        List<User> users = userRepository.findAllByAppIdContains(app);
        model.addAttribute("users", users);
        return "AllAcc";
    }
}