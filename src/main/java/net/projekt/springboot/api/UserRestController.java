package net.projekt.springboot.api;

import net.projekt.springboot.Exceptions.NotFoundException;
import net.projekt.springboot.model.User;
import net.projekt.springboot.repository.UserRepository;
import net.projekt.springboot.service.UserServiceImpl;
import net.projekt.springboot.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/users")
    List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/api/users")
    User addUser(@RequestBody @Valid UserRegistrationDto newUser) {
        return userService.save(newUser);
    }

    @GetMapping("/api/users/{id}")
    User one(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @GetMapping("/api/users/poland")
    List<User> FromPolandWithApps() {
        return userRepository
                .findAll()
                .stream()
                .filter(c -> c.getAppId().size() > 0 && c.getCountry().equals("Polska"))
                .collect(Collectors.toList());
    }

    @PutMapping("/api/users/{id}")
    User updateUser(@RequestBody UserRegistrationDto newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    user.setCountry(newUser.getCountry());
                    user.setUsername(newUser.getUsername());
                    user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    @DeleteMapping("/api/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
