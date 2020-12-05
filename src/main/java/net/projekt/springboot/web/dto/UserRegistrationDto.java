package net.projekt.springboot.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

