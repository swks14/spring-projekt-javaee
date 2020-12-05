package net.projekt.springboot.repository;

import net.projekt.springboot.model.Application;
import net.projekt.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAll();

    Optional<Application> findById(Long id);

    void deleteById(Long id);

    Application findByName(String name);

    List<Application> findAllByUserIdContains(User user);
}

