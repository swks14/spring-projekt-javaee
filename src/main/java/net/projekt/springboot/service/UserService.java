package net.projekt.springboot.service;

import net.projekt.springboot.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.projekt.springboot.model.User;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto userRegistrationDto);

}
