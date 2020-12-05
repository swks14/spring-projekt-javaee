package net.projekt.springboot.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data @NoArgsConstructor
@Table(name =  "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String domain;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_apps",
            joinColumns = @JoinColumn(
                    name = "application_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private Set<User> userId = new HashSet<>();

    public Application(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }
}
