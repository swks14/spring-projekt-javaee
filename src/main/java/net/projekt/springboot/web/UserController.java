package net.projekt.springboot.web;

import lombok.Data;
import net.projekt.springboot.model.User;
import net.projekt.springboot.repository.ApplicationRepository;
import net.projekt.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Data
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    @GetMapping("/accdelete")
    public String application() {
        return "AccDelete";
    }

    @RequestMapping(value = "/accdelete", method = RequestMethod.POST)
    public String DeleteAccount(Principal principal) {
        userRepository.deleteById(userRepository.findByEmail(principal.getName()).getId());
        return "login";
    }

    @GetMapping("/myacc")
    public String Myacc() {
        return "MyAcc";
    }

    @GetMapping("/allacc")
    public String application(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "AllAcc";
    }

    @GetMapping("/accfromapp")
    public String GetIdtoShow() {
        return "FindUsersFromApp";
    }

    @RequestMapping(value = "/accfromapp", method = RequestMethod.POST)
    public String ShowUsersFromApp(@RequestParam Long id, Model model) {
        model.addAttribute(userRepository.findAllByAppIdContains(applicationRepository.findById(id).orElse(null)));

        return "redirect:/application";
    }
}
