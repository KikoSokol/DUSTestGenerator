package com.privateAPI.DUSTestGenerator.petri_nets.service.impl;

import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.DefinitionPTIOM0;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PrescriptionPnDto;
import com.privateAPI.DUSTestGenerator.petri_nets.generator.PetriNetGenerator;
import com.privateAPI.DUSTestGenerator.petri_nets.service.PetriNetService;
import com.privateAPI.DUSTestGenerator.petri_nets.validator.PetriNetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

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
        ConstraintViolationException exception = petriNetValidator.validate(petriNetGeneratorRequest);

        if(exception != null)
            throw exception;

        return petriNetGenerator.generateRandomPetriNet(petriNetGeneratorRequest);
    }

    @Override
    public PrescriptionPnDto getRandomPetriNetWithPrescriptionPN(PetriNetGeneratorRequest petriNetGeneratorRequest) throws ConstraintViolationException
    {
        ConstraintViolationException exception = this.petriNetValidator.validate(petriNetGeneratorRequest);

        if(exception != null)
            throw exception;

        PetriNetDto petriNetDto = getRandomPetriNet(petriNetGeneratorRequest);
        return new PrescriptionPnDto(petriNetDto);
    }

    @Override
    public DefinitionPTIOM0 getRandomPetriNetWithDefinitionPNIOM0C(PetriNetGeneratorRequest petriNetGeneratorRequest) throws ConstraintViolationException
    {

        ConstraintViolationException exception = this.petriNetValidator.validate(petriNetGeneratorRequest);

        if(exception != null)
            throw exception;

        PetriNetDto petriNetDto = getRandomPetriNet(petriNetGeneratorRequest);
        return new DefinitionPTIOM0(petriNetDto);
    }
}
