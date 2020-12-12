package net.projekt.springboot.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDto {

    private Long id;
    private String name;
    private String domain;


    public ApplicationDto(Long id, String name, String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    public ApplicationDto(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }
}
