package com.palazzisoft.gerbio.integrator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order")
@Slf4j
public class CallbackController {

    @PostMapping
    public ResponseEntity<String> get(@RequestBody Object object) {
        log.info("Recibo {}", object);
        return ResponseEntity.ok(object.toString());
    }
}

/*
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/talent")
@Slf4j
public class TalentController {

    private final TalentService talentService;
    private final TalentExperienceService experienceService;
    private final TalentEducationService educationService;
    private final TalentCertificationService certificationService;
    private final TalentProfitService talentProfitService;
    private final TalentSoftSkillService softSkillService;
    private final TalentLanguageCertService languageCertService;
    private final MapperFacade mapper;

    @ApiOperation(
            value = "Create or update talent",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @PutMapping
    public ResponseEntity<TalentDTO> createOrUpdateTalent(@Valid @RequestBody final TalentDTO talentDTO) {
        log.info("Creating or Updating talent");
        Talent talent = talentService.create(mapper.map(talentDTO, Talent.class));
        return ResponseEntity.ok(mapper.map(talent, TalentDTO.class));
    }
 */