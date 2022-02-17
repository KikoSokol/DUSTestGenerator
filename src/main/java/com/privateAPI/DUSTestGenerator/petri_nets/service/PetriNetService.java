package com.privateAPI.DUSTestGenerator.petri_nets.service;

import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.DefinitionPTIOM0;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PrescriptionPnDto;

public interface PetriNetService
{
    PetriNetDto getRandomPetriNet(PetriNetGeneratorRequest petriNetGeneratorRequest);

    PrescriptionPnDto getRandomPetriNetWithPrescriptionPN(PetriNetGeneratorRequest petriNetGeneratorRequest);

    DefinitionPTIOM0 getRandomPetriNetWithDefinitionPNIOM0C(PetriNetGeneratorRequest petriNetGeneratorRequest);
}
