package net.projekt.springboot.web;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.Data;
import net.projekt.springboot.model.User;
import net.projekt.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Data
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

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
}
