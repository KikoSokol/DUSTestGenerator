package com.privateAPI.DUSTestGenerator.petri_nets.controller;

import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.generator.PetriNetGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("petri-net-test")
public class PetriNetControllerTest
{

    @CrossOrigin(origins = "https://lubossremanak.site")
    @GetMapping("generator")
    public ResponseEntity getRandomPetriNet()
    {

        PetriNetGeneratorRequest petriNetGeneratorRequest = new PetriNetGeneratorRequest(3,3,5,5, 0, 20, 0, 5, 1, 5);

        PetriNetGenerator petriNetGenerator = new PetriNetGenerator();
        return new ResponseEntity<>(petriNetGenerator.generateRandomPetriNet(petriNetGeneratorRequest), HttpStatus.OK);
    }
}
