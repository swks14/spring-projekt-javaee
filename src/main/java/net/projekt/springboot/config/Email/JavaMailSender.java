package net.projekt.springboot.config.Email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class JavaMailSender {
    @org.springframework.beans.factory.annotation.Value("${spring.mail.host}")
    private String host;
    @org.springframework.beans.factory.annotation.Value("${spring.mail.port}")
    private int port;
    @org.springframework.beans.factory.annotation.Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

}
