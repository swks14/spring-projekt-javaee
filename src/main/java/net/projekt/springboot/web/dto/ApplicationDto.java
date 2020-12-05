package net.projekt.springboot.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
