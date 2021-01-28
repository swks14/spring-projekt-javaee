package net.projekt.springboot.config.Email;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Getter
@Setter
public class Feedback {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Min(10)
    private String feedback;

    public Feedback(String name, @Email String email, @Min(10) String feedback) {
        this.name = name;
        this.email = email;
        this.feedback = feedback;
    }
}
