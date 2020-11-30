package net.projekt.springboot.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.projekt.springboot.model.Role;

import java.util.Collection;

@Data
@NoArgsConstructor
public class ApplicationDto {

    private Long id;
    private String name;
    private String domain;
    private double version;


    public ApplicationDto(Long id, String name, String domain, double version, Collection<Role> roles) {
        this.id = id;
        this.name = name;
        this.domain = domain;
        this.version = version;
    }
}
