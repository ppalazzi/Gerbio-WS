package com.palazzisoft.gerbio.integrator.controller;

import com.palazzisoft.gerbio.integrator.model.IntegratorError;
import com.palazzisoft.gerbio.integrator.service.IntegratorErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/errors")
@Slf4j
@Profile("!test")
public class IntegratorErrorController {

    private final IntegratorErrorService integratorErrorService;

    public IntegratorErrorController(IntegratorErrorService integratorErrorService) {
        this.integratorErrorService = integratorErrorService;
    }

    @GetMapping
    public ResponseEntity<List<IntegratorError>> getAllErrors() {
        List<IntegratorError> integratorErrors = integratorErrorService.getAll();
        return ResponseEntity.ok(integratorErrors);
    }
}