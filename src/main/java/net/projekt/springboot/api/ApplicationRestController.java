package net.projekt.springboot.api;

import net.projekt.springboot.Exceptions.NotFoundException;
import net.projekt.springboot.model.Application;
import net.projekt.springboot.repository.ApplicationRepository;
import net.projekt.springboot.repository.UserRepository;
import net.projekt.springboot.service.ApplicationServiceImpl;
import net.projekt.springboot.web.dto.ApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationRestController {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationServiceImpl applicationService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/apps")
    List<Application> all() {
        return applicationRepository.findAll();
    }

    @PostMapping("/api/apps")
    Application addApplication(@RequestBody ApplicationDto application) {
        return applicationService.save(application);
    }

    @GetMapping("/api/apps/{id}")
    Application one(@PathVariable Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @PutMapping("/api/apps/{id}")
    Application updateApp(@RequestBody ApplicationDto newApplication, @PathVariable Long id) {
        return applicationRepository.findById(id)
                .map(application -> {
                    application.setName(newApplication.getName());
                    application.setDomain(newApplication.getDomain());

                    return applicationService.save(application);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    @DeleteMapping("/api/apps/{id}")
    void deleteApp(@PathVariable Long id) {
        Application app = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        app.getUserId().clear();
        applicationService.deleteById(id);
    }
}
