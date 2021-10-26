package com.palazzisoft.gerbio.integrator.service;

import com.palazzisoft.gerbio.integrator.model.IntegratorError;
import com.palazzisoft.gerbio.integrator.repository.IntegratorErrorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class IntegratorErrorService {

    private final IntegratorErrorRepository integratorErrorRepository;

    public IntegratorErrorService(final IntegratorErrorRepository integratorErrorRepository) {
        this.integratorErrorRepository = integratorErrorRepository;
    }

    public List<IntegratorError> getAll() {
        return integratorErrorRepository.findAll();
    }

    public void saveError (IntegratorError integratorError) {
        integratorErrorRepository.save(integratorError);
    }

    @Transactional
    public void saveListOfErrors(List<IntegratorError> integratorErrors) {
        integratorErrors.forEach(integratorErrorRepository::save);
    }

    public List<IntegratorError> getErrorsByDate(Date dateOfError) {
        List<IntegratorError> integratorErrors = new ArrayList<>();
        return integratorErrors;
    }
}