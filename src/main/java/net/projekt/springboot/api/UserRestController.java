package net.projekt.springboot.api;

import net.projekt.springboot.Exceptions.NotFoundException;
import net.projekt.springboot.model.User;
import net.projekt.springboot.repository.UserRepository;
import net.projekt.springboot.service.UserServiceImpl;
import net.projekt.springboot.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;


    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/users")
    List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/api/users")
    User addUser(@RequestBody UserRegistrationDto newUser) {
        return userService.save(newUser);
    }

    @GetMapping("/api/users/{id}")
    User one(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @PutMapping("/api/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    user.setCountry(newUser.getCountry());
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    @DeleteMapping("/api/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
