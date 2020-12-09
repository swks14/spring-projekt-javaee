package net.projekt.springboot.service;


import net.projekt.springboot.model.Application;
import net.projekt.springboot.repository.ApplicationRepository;
import net.projekt.springboot.web.dto.ApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService{
    @Autowired
    private ApplicationRepository applicationRepository;


    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        super();
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application save(ApplicationDto applicationDto) {
        Application application = new Application(applicationDto.getName(), applicationDto.getDomain());

        return applicationRepository.save(application);
    }
    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> findAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);

    }

    @Override
    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }


}


