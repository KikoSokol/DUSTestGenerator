package com.privateAPI.DUSTestGenerator.petri_nets.service.impl;

import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.generator.PetriNetGenerator;
import com.privateAPI.DUSTestGenerator.petri_nets.service.PetriNetService;
import com.privateAPI.DUSTestGenerator.petri_nets.validator.PetriNetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class PetriNetServiceImpl implements PetriNetService
{
    @Autowired
    private PetriNetGenerator petriNetGenerator;

    @Autowired
    private PetriNetValidator petriNetValidator;


    @Override
    public PetriNetDto getRandomPetriNet(PetriNetGeneratorRequest petriNetGeneratorRequest)
    {
        ConstraintViolationException exception = petriNetValidator.validatePetriNetGeneratorRequest(petriNetGeneratorRequest);

        if(exception != null)
            throw exception;

        return petriNetGenerator.generateRandomPetriNet(petriNetGeneratorRequest);
    }
}
