package net.projekt.springboot.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String username;
    private String password;

    public UserRegistrationDto(String firstName, String lastName, String email, String country, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.username = username;
        this.password = password;
    }
}

