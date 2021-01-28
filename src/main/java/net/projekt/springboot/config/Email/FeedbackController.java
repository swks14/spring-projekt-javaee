package net.projekt.springboot.config.Email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;

@Controller
public class FeedbackController {
    private JavaMailSender javaMailSender;

    public FeedbackController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void Sendmail(String email) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.javaMailSender.getHost());
        mailSender.setPort(this.javaMailSender.getPort());
        mailSender.setUsername(this.javaMailSender.getUsername());
        mailSender.setPassword(this.javaMailSender.getPassword());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply@projektee.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Pomyslnie zarejestrowano");
        simpleMailMessage.setText("feedback.getFeedback()");

        mailSender.send(simpleMailMessage);
    }
}
