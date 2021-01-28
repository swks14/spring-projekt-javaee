package net.projekt.springboot.web.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String username;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "haslo musi zawierac conajmniej 8 znaków" +
            "w tym conajmniej jedna litera mała oraz duża i jedna cyfra ")
    private String password;

    public UserRegistrationDto(String firstName, String lastName, String email, String country, String password, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.password = password;
        this.username = username;
    }
}

