package net.projekt.springboot.web;

import net.projekt.springboot.Exceptions.NotFoundException;
import net.projekt.springboot.model.Role;
import net.projekt.springboot.model.User;
import net.projekt.springboot.repository.ApplicationRepository;
import net.projekt.springboot.repository.UserRepository;
import net.projekt.springboot.service.UserServiceImpl;
import net.projekt.springboot.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserServiceImpl userService;
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
    public String applicationUpdate(@Valid Model model) {
        model.addAttribute("user", new User());
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
                    user.setPassword(NewUser.getPassword());
                    user.setRoles(Arrays.asList(new Role("ROLE_USER")));
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
        users = userRepository.findAll();
        model.addAttribute("users", users);
        return "AllAcc";
    }

    @GetMapping("/accfromapp")
    public String GetIdtoShow() {
        return "FindUsersFromApp";
    }

    @RequestMapping(value = "/accfromapp", method = RequestMethod.POST)
    public String ShowUsersFromApp(@RequestParam Long id, @Valid Model model) {
        model.addAttribute(userRepository.findAllByAppIdContains(applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(id))));

        return "redirect:/application";
    }

    @PostMapping("/allacc/tocsv")
    public String accountsToCsv() throws IOException {
        FileWriter f = new FileWriter("users.csv");
        for (User user : users) {
            f.write("\n" + user.toString());
        }
        f.close();
        return "redirect:/allacc";
    }
}
