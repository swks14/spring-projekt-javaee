package net.projekt.springboot.service;

import net.projekt.springboot.model.Application;
import net.projekt.springboot.web.dto.ApplicationDto;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application save(ApplicationDto applicationDto);
    List<Application> findAllApplications();
    Optional<Application> findById(Long id);
    void deleteById(Long id);
}