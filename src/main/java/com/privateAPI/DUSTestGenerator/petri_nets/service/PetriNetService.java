package com.privateAPI.DUSTestGenerator.petri_nets.service;

import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;

public interface PetriNetService
{
    PetriNetDto getRandomPetriNet(PetriNetGeneratorRequest petriNetGeneratorRequest);
}
