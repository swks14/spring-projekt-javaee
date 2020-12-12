package net.projekt.springboot.repository;

import net.projekt.springboot.model.Application;
import net.projekt.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    void deleteById(Long id);

    User findByUsername(String username);

    List<User> findAllByAppIdContains(Application application);

}
