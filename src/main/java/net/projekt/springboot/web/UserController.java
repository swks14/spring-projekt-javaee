package net.projekt.springboot.web;

import net.projekt.springboot.Exceptions.NotFoundException;
import net.projekt.springboot.model.User;
import net.projekt.springboot.repository.ApplicationRepository;
import net.projekt.springboot.repository.UserRepository;
import net.projekt.springboot.service.UserServiceImpl;
import net.projekt.springboot.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private List<User> users = new ArrayList<>();


    @GetMapping("/accdelete")
    public String applicationDelete() {
        return "AccDelete";
    }

    @RequestMapping(value = "/accdelete", method = RequestMethod.POST)
    public String DeleteAccount(Principal principal) {
        userRepository.deleteById(userRepository.findByEmail(principal.getName()).getId());
        return "login";
    }

    @GetMapping("/accupdate")
    public String applicationUpdate(@Valid Model model, Principal principal) {
        User userToUpdate = userRepository.findById(userRepository.findByEmail(principal.getName())
                .getId()).orElseThrow(() -> new NotFoundException(userRepository.findByEmail(principal.getName())
                .getId()));
        model.addAttribute("user", userToUpdate);
        return "AccEdit";
    }

    @RequestMapping(value = "/accupdate", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") @Valid UserRegistrationDto NewUser, Principal principal) {
        userRepository.findById(userRepository.findByEmail(principal.getName()).getId())
                .map(user -> {
                    user.setFirstName(NewUser.getFirstName());
                    user.setLastName(NewUser.getLastName());
                    user.setEmail(NewUser.getEmail());
                    user.setCountry(NewUser.getCountry());
                    user.setUsername(NewUser.getUsername());
                    user.setPassword(passwordEncoder.encode(NewUser.getPassword()));
                    userRepository.save(user);
                    return "redirect:/logout";
                })
                .orElseGet(() -> "redirect:/logout");
        return "redirect:/logout";
    }

    @GetMapping("/myacc")
    public String Myacc() {
        return "MyAcc";
    }

    @GetMapping("/allacc")
    public String application(@Valid Model model) {
        users = userRepository.findAllByOrderByIdAsc();
        model.addAttribute("users", users);
        return "AllAcc";
    }

    @GetMapping("/accfromapp")
    public String GetIdtoShow() {
        return "FindUsersFromApp";
    }

    @RequestMapping(value = "/accfromapp", method = RequestMethod.POST)
    public String ShowUsersFromApp(@RequestParam Long id, @Valid Model model) {
        model.addAttribute("users",userRepository.findAllByAppIdContains(applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(id))));

        return "AllAcc";
    }

    @PostMapping("/allacc/tocsv")
    public String accountsToCsv() throws IOException {
        String path = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "users.csv";
        FileWriter f = new FileWriter(path);
        for (User user : users) {
            f.write("\n" + user.toString());
        }
        f.close();
        return "redirect:/allacc";
    }

    @GetMapping("/accdelete/{id}")
    public String accountDeleteId(@PathVariable Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userRepository.deleteById(userToDelete.getId());
        return "redirect:/allacc";
    }

    @GetMapping("/accedit/{id}")
    public String accountEditId(@PathVariable Long id, Model model) {
        User userToFind = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        model.addAttribute("user", userToFind);
        return "AccIdEdit";
    }

    @PostMapping("/accedit/id")
    public String updateUserId(@ModelAttribute("user") @Valid UserRegistrationDto NewUser, Principal principal) {
        userRepository.findById(userRepository.findByEmail(principal.getName()).getId())
                .map(user -> {
                    user.setFirstName(NewUser.getFirstName());
                    user.setLastName(NewUser.getLastName());
                    user.setEmail(NewUser.getEmail());
                    user.setCountry(NewUser.getCountry());
                    user.setUsername(NewUser.getUsername());
                    user.setPassword(passwordEncoder.encode(NewUser.getPassword()));
                    userRepository.save(user);
                    return "redirect:/allacc";
                })
                .orElseGet(() -> "redirect:/allacc");
        return "redirect:/allacc";
    }

}
